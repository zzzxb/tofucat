package cn.tofucat.protocol.cmpp.cmpp2;

import java.util.function.Supplier;

/**
 * @author zzzxb
 * 2024/11/19
 */
public enum ServiceType {
    ACTIVE(ActiveService::new),
    LOGIN(LoginService::new),
    SUBMIT(SubmitService::new),
    DELIVERED(DeliveredService::new);

    private final Supplier<CtxProcess> supplier;

    ServiceType(Supplier<CtxProcess> supplier) {
        this.supplier = supplier;
    }

    public CtxProcess get() {
        return supplier.get();
    }
}