package cn.ultronxr.qqrobot.listener.listenerHost;

import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.SimpleListenerHost;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class PersonMsgListener extends SimpleListenerHost {




    /**
     * 异常处理
     * 上面的所有EventHandler中抛出的Exception都在这里接收并处理
     */
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        super.handleException(context, exception);
    }
}

