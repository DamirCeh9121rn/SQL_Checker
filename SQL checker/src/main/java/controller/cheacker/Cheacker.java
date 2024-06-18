package controller.cheacker;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONArray;

public interface Cheacker {

    public boolean Check(String string);

    public boolean bulkCheck(String string);
}
