package com.tao.opensight.mviDispatcher

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

// MVI架构中的分发器基类，支持泛型T
open class MviDispatcherKTX<T : Any> : ViewModel(), DefaultLifecycleObserver {
    // 初始版本常量，用于控制状态更新
    private var version = START_VERSION
    private var currentVersion = START_VERSION
    private var observerCount = 0 // 观察者计数器

    // 延迟初始化共享流，配置为最早发出的元素会被丢弃
    private val _sharedFlow: MutableSharedFlow<ConsumeOnceValue<T>> by lazy {
        MutableSharedFlow(
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
            extraBufferCapacity = initQueueMaxLength(),
            replay = initQueueMaxLength()
        )
    }

    // 配置流的队列长度，默认为10
    protected open fun initQueueMaxLength(): Int {
        return DEFAULT_QUEUE_LENGTH
    }

    // 输出到Activity的封装方法
    fun output(activity: AppCompatActivity?, observer: (T) -> Unit) {
        outputTo(activity, observer)
    }

    // 输出到Fragment的封装方法
    fun output(fragment: Fragment?, observer: (T) -> Unit) {
        outputTo(fragment?.viewLifecycleOwner, observer)
    }

    // 根据LifecycleOwner绑定生命周期并监听状态更新
    private fun outputTo(lifecycleOwner: LifecycleOwner?, observer: (T) -> Unit) {
        currentVersion = version
        observerCount++
        lifecycleOwner?.lifecycle?.addObserver(this)
        lifecycleOwner?.lifecycleScope?.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                _sharedFlow.collect {
                    if (version > currentVersion) {
                        if (it.isAllConsumed) return@collect
                        ++it.consumeCount
                        observer(it.value)
                        if (it.consumeCount == observerCount) it.isAllConsumed = true
                    }
                }
            }
        }
    }

    // 当LifecycleOwner销毁时减少观察者计数
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        observerCount--
    }

    // 发送新状态到流中
    protected suspend fun sendResult(intent: T) {
        version++
        _sharedFlow.emit(ConsumeOnceValue(value = intent))
    }

    // 处理外部传入的意图
    fun input(intent: T) {
        viewModelScope.launch { onHandle(intent) }
    }

    // 意图处理函数，可被子类覆盖
    protected open suspend fun onHandle(intent: T) {}

    // 封装一次性消费的值，包含消费计数和是否已全部消费的标志
    private data class ConsumeOnceValue<T>(
        var consumeCount: Int = 0,
        var isAllConsumed: Boolean = false,
        val value: T
    )

    // 配置常量
    companion object {
        private const val DEFAULT_QUEUE_LENGTH = 10
        private const val START_VERSION = -1
    }
}