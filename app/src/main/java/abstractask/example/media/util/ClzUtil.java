package abstractask.example.media.util;

public class ClzUtil {

    @SuppressWarnings("unchecked")
    public static <R> R getCast(Object o){
        return o == null ? null : (R)o;
    }


}
