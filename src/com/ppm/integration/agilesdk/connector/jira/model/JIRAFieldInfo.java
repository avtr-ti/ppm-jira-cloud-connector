
package com.ppm.integration.agilesdk.connector.jira.model;

import com.ppm.integration.agilesdk.model.AgileEntityField;
import com.ppm.integration.agilesdk.model.AgileEntityFieldValue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JIRAFieldInfo {

    private String key;

    private String name;

    private boolean isList = false;

    private String type;

    private String system;

    private String items;

    private List<AgileEntityField> allowedValues = null;


    public static JIRAFieldInfo fromJSONObject(JSONObject obj, String key) {
        try {
            JIRAFieldInfo fieldInfo = new JIRAFieldInfo();
            fieldInfo.setKey(key);
            fieldInfo.setName(obj.getString("name"));

            if (obj.has("allowedValues")) {
                JSONArray allowedValues = obj.getJSONArray("allowedValues");
                if (allowedValues != null) {
                    fieldInfo.setAllowedValues(new ArrayList<AgileEntityField>(allowedValues.length()));

                    for (int i = 0 ; i < allowedValues.length() ; i++) {
                        AgileEntityField listValue = new AgileEntityField();
                        JSONObject listValueObj = allowedValues.getJSONObject(i);
                        listValue.setKey(listValueObj.getString("id"));
                        AgileEntityFieldValue value = new AgileEntityFieldValue(listValueObj.getString("name"), listValueObj.getString("id"));
                        listValue.setValue(value);
                        fieldInfo.getAllowedValues().add(listValue);
                    }
                }
            }

            JSONObject schema = obj.getJSONObject("schema");

                if (schema != null) {
                    if (schema.has("type")) {
                        fieldInfo.setType(schema.getString("type"));
                    }
                    if (schema.has("system")) {
                        fieldInfo.setSystem(schema.getString("system"));
                    }
                    if (schema.has("items")) {
                        fieldInfo.setItems(schema.getString("items"));
                    }
                }

            if (fieldInfo.getAllowedValues() != null  || "array".equals(fieldInfo.getType())) {
                fieldInfo.setList(true);
            }

            return fieldInfo;
        } catch (JSONException e) {
            throw new RuntimeException("Error while reading JSon defintion of Issue Type", e);
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isList() {
        return isList;
    }

    public void setList(boolean list) {
        isList = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public List<AgileEntityField> getAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(List<AgileEntityField> allowedValues) {
        this.allowedValues = allowedValues;
    }
}
