package com.akashi.annotation_compiler

import com.akashi.annotations.BindView
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import java.io.Writer
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.tools.Diagnostic

/**
 * 注解处理程序，使javac在编译阶段能调用到此处的process()，最终生成新代码
 * 1.打注解@AutoService, 注册Process.class（kotlin有bug，无法生成源码，改用静态注册）
 * 2.继承AbstractProcessor
 */
//@AutoService(Processor::class)
class Compiler : AbstractProcessor() {

    /**
     * 创建文件
     */
    private lateinit var filer: Filer

    /**
     * 初始化入口
     */
    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        processingEnv ?: return
        filer = processingEnv.filer
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(BindView::class.java.canonicalName)
    }


    override fun process(set: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        // Build 日志输出
        println("Akashi-----")
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "Akashi----${set}")

        if (set.isNullOrEmpty()) return false

        // TypeElement //类
        // ExecutableElement //方法
        // VariableElement // 属性
        val elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(BindView::class.java)

        // 对指定注解（BindView::class.java）的elementsAnnotatedWith进行分类（按照variable属性）
        // <class to variables>
        val map = mutableMapOf<String, MutableList<VariableElement>>()
        for (element in elementsAnnotatedWith) {
            val variableElement = element as? VariableElement ?: return false
            // key - which class (name)
            val activityName = variableElement.enclosingElement.simpleName.toString()

            // value - variables
            var variableElements: MutableList<VariableElement>? = map[activityName]
            if (variableElements == null) {
                variableElements = mutableListOf()
                map[activityName] = variableElements
            }
            variableElements.add(variableElement)
        }

        //|   key     |   value
        //-------------------------
        //| activity1 | variables
        //| activity2 | variables
        //| activity3 | variables
        runPoet(map)

        return false
    }

    /**
     * 使用kotlinpoet生成source file
     */
    private fun runPoet(map: MutableMap<String, MutableList<VariableElement>>) {
        val iterator = map.keys.iterator()
        while (iterator.hasNext()) {
            val activityName = iterator.next()
            val variableElements = map[activityName] ?: return
            // package name. enclosingElement: 对象的包裹对象
            val enclosingElement = variableElements[0].enclosingElement
            val packageName = processingEnv.elementUtils.getPackageOf(enclosingElement).toString()
            // class name
            val className = "${activityName}_ViewBinding"

            try {
                val superInterface = ClassName(packageName, "IBinder")
                val activity = ClassName(packageName, activityName)

                // 1. 创建类，构造函数
                val typeSpec = TypeSpec.classBuilder(className)
                    // 添加父类IBinder和泛型约束
                    .addSuperinterface(
                        superInterface.parameterizedBy(activity)
                    )

                // 2. 创建方法
                val func = FunSpec.builder("bind")
                    .addModifiers(KModifier.OVERRIDE)
                    .addParameter("target", activity)

                for (variableElement in variableElements) {
                    // 变量名
                    val variableName = variableElement.simpleName.toString()
                    // 变量id
                    val id = variableElement.getAnnotation(BindView::class.java).value
                    // 拼接
                    func.addStatement("target.${variableName}=target.findViewById(${id})")
                }


                // 3. 创建文件
                val sourceFile =
                    FileSpec.builder(packageName, className)
                        .addType(
                            // 添加构造方法
                            typeSpec
                                // 添加重写方法@Bind
                                .addFunction(
                                    func.build()
                                )
                                .build()
                        )
                        .build()

                sourceFile.writeTo(filer)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

//    package com.akashi.roadmap.annotation;
//    import com.akashi.roadmap.annotation.IBinder;
//    public class AnnotationActivity_ViewBinding implements IBinder<com.akashi.roadmap.annotation.AnnotationActivity> {
//        @Override
//        public void bind(com.akashi.roadmap.annotation.AnnotationActivity target) {
//            target.textView = (android.widget.TextView) target.findViewById(2131231145);
//
//        }
//    }
    /**
     * 手写source file
     * 修改文件位置会导致修改
     * 不推荐
     */
    private fun runFool(map: MutableMap<String, MutableList<VariableElement>>) {
        var writer: Writer? = null
        val iterator = map.keys.iterator()
        while (iterator.hasNext()) {
            val activityName = iterator.next()
            val variableElements = map[activityName] ?: return
            // 利用api获取packageName
            val enclosingElement = variableElements[0].enclosingElement
            val packageName = processingEnv.elementUtils.getPackageOf(enclosingElement).toString()

            try {
                // 打开写入流 手写代码
                val sourceFile =
                    filer.createSourceFile("${packageName}.${activityName}_ViewBinding")
                writer = sourceFile.openWriter().apply {
                    // package com.akashi.dn_butterknife;
                    this.write("package ${packageName};\n")
                    // import com.akashi.dn_butterknife.Binder;
                    this.write("import ${packageName}.IBinder;\n")
                    // public class AnnotationActivity_ViewBinding implements IBinder<
                    // com.akashi.butterknife.AnnotationActivity>{
                    this.write("public class ${activityName}_ViewBinding implements IBinder<${packageName}.${activityName}>{\n")
                    // public void bind(com.akashi.dn_butterknife.AnnotationActivity target) {
                    this.write("@Override\n public void bind(${packageName}.${activityName} target){")
                    // target.tvText=(android.widget.TextView)target.findViewById(1231255);
                    for (variableElement in variableElements) {
                        // 变量名
                        val variableName = variableElement.simpleName.toString()
                        // 变量id
                        val id = variableElement.getAnnotation(BindView::class.java).value
                        // 变量类型
                        val typeMirror = variableElement.asType()
                        this.write("target.${variableName}=(${typeMirror})target.findViewById(${id});\n")
                    }
                }

                writer.write("\n}}")

            } catch (e: Throwable) {
                e.printStackTrace()
            } finally {
                try {
                    writer?.close()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }
}