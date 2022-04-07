package com.akashi.roadmap.annotation

/**
 * 用来绑定Activity
 */
interface IBinder<T> {
    fun bind(target: T)
}
// build的时候生成接口实现类
// public class MainActivity_ViewBinding implements IBinder<...> {
//      override fun bind(target: ...MainActivity) {
//          target.textView = (android.widget.TextView)target.findViewById(1238585818)
//      }
// }