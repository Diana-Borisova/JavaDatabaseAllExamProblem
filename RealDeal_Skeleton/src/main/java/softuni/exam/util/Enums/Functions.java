package softuni.exam.util.Enums;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;

public enum Functions {
    ;
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final ModelMapper MODEL_MAPPER = new ModelMapper();
    public static final StringBuilder STRING_BUILDER = new StringBuilder();
}