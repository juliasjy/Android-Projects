package edu.rose_hulman.suj1.exam2_suj1;

/**
 * Created by suj1 on 1/8/2017.
 */

        import android.content.Context;

        import com.fasterxml.jackson.core.type.TypeReference;
        import com.fasterxml.jackson.databind.ObjectMapper;

        import java.io.IOException;
        import java.io.InputStream;
        import java.util.List;

/**
 * Created by Matt Boutell on 7/4/2016.
 * Rose-Hulman Institute of Technology.
 * Covered by MIT license.
 */
public class FileUtils {
    public static List<Book> loadWordsFromJsonArray(Context context) {
        InputStream is = context.getResources().openRawResource(R.raw.book_info_json);
        List<Book> words = null;
        try {
            words = new ObjectMapper().readValue(is, new TypeReference<List<Book>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }
}
