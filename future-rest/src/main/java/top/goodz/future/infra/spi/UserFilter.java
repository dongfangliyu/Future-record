package top.goodz.future.infra.spi;

import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.filter.TokenFilter;

public class UserFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        System.out.printf("111111");
        Result invoke = invoker.invoke(invocation);
        return invoke;
    }
}
