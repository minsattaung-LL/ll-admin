package pro.linuxlab.reservation.superadmin.util;

import pro.linuxlab.reservation.superadmin.BaseResponse;

import java.util.Map;

public interface Util {
    String toJson(Object object);

    String generateID (String prefix, Long count);

    String toJsonPretty(Object object);

    BaseResponse generateDefaultResponse(Object result);

    <T> T toObject(String json, Class t);

    Map<String, Object> convertObjectToMap(Object obj);
}
