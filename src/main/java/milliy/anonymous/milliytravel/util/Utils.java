package milliy.anonymous.milliytravel.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.*;
import io.gsonfire.GsonFireBuilder;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.dto.Result;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;

@Slf4j
public class Utils {

    public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static Gson gson;

    private static ObjectMapper mapper;

    public static Gson gson() {
        if (gson != null) {
            return gson;
        }
        gson = new GsonFireBuilder().enableExposeMethodResult().createGsonBuilder()//.setPrettyPrinting()
                .registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
                    if (src == src.longValue()) {
                        return new JsonPrimitive(src.longValue());
                    }
                    return new JsonPrimitive(src);
                }).setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return isFieldInSuperclass(f.getDeclaringClass(), f.getName());
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }

                    private boolean isFieldInSuperclass(Class<?> subclass, String fieldName) {
                        Class<?> superclass = subclass.getSuperclass();
                        Field field;

                        while (superclass != null) {
                            field = getField(superclass, fieldName);

                            if (field != null) {
                                return true;
                            }

                            superclass = superclass.getSuperclass();
                        }

                        return false;
                    }

                    private Field getField(Class<?> theClass, String fieldName) {
                        try {
                            return theClass.getDeclaredField(fieldName);
                        } catch (Exception e) {
                            return null;
                        }
                    }
                }).setDateFormat(STANDARD_DATE_FORMAT).create();
        return gson;
    }

    public static ObjectMapper mapper() {
        if (mapper != null) {
            return mapper;
        }
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

    public static ResponseEntity<String> createResponse(Object object) {
        return createResponse(object, HttpStatus.OK);
    }

    public static ResponseEntity<String> createResponse(Object object, HttpStatus status) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        if (object instanceof JsonElement) {
            return new ResponseEntity<>(gson().toJson(object), responseHeaders, status);
        }

        try {
            JsonElement innerRestErrorData = null;
            if (object instanceof Result re) {
                if (re.getData() != null && re.getData() instanceof JsonElement) {
                    innerRestErrorData = (JsonElement) re.getData();
                    re.setData(null);
                }
            }

            String jsonStr = mapper().writeValueAsString(object);
            if (innerRestErrorData != null) {
                JsonObject parsedObj = JsonParser.parseString(jsonStr).getAsJsonObject();
                parsedObj.add("data", innerRestErrorData);
                jsonStr = parsedObj.toString();
            }

            return new ResponseEntity<>(jsonStr, responseHeaders, status);
        } catch (Exception e) {
            log.error("", e);
        }

        return null;
    }

}
