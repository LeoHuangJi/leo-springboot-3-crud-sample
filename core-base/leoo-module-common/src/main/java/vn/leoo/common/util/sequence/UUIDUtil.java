package vn.leoo.common.util.sequence;

import java.util.UUID;

public class UUIDUtil {

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

}
