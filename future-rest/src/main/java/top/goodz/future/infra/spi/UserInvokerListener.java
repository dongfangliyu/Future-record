package top.goodz.future.infra.spi;

import org.apache.dubbo.common.extension.SPI;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.InvokerListener;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class UserInvokerListener  implements InvokerListener {
    @Override
    public void referred(Invoker<?> invoker) throws RpcException {

        Class<?> anInterface = invoker.getInterface();
        for (Field declaredField : anInterface.getDeclaredFields()) {
            System.out.printf("" + declaredField);
        }



    }

    @Override
    public void destroyed(Invoker<?> invoker) {

        Class<?> anInterface = invoker.getInterface();
        System.out.printf("************" + invoker);
    }
}
