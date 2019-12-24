package edu.csc207.recruitment.storage;

import com.google.gson.*;
import edu.csc207.recruitment.document.DocumentDatabase;
import edu.csc207.recruitment.model.db.RecruitmentSystem;
import edu.csc207.recruitment.model.user.User;

import java.lang.reflect.Type;

/**
 * A class for serializing and deserializing all classes, documents, etc. to a file
 */
public class GsonManager {


    /**
     * Formats a json string for a database
     *
     * @param documentDatabase a DocumentDatabase
     * @return a json formatted string corresponding to the database
     */
    public String serializeDocumentDatabase(DocumentDatabase documentDatabase) {
        return new Gson().toJson(documentDatabase);
    }


    /**
     * deserializes DocumentDatabase
     *
     * @param json a json string corresponding to a DocumentDatabase
     * @return the stored DocumentDatabase
     */
    public DocumentDatabase deserializeDocumentDatabase(String json) {
        Gson gson = new GsonBuilder().registerTypeAdapter(User.class, new InterfaceAdapter<User>()).create();
        return gson.fromJson(json, DocumentDatabase.class);
    }


    /**
     * Formats a json string for a RecruitmentSystem
     *
     * @param recruitmentSystem a RecruitmentSystem
     * @return a json formatted string corresponding to the RecruitmentSystem
     */
    public String serializeRecruitmentSystem(RecruitmentSystem recruitmentSystem) {
        Gson gson = new GsonBuilder().registerTypeAdapter(User.class, new InterfaceAdapter<User>()).create();
        return gson.toJson(recruitmentSystem);
    }

    /**
     * deserializes RecruitmentSystem
     *
     * @param json a json string corresponding to a RecruitmentSystem
     * @return the stored RecruitmentSystem
     */
    public RecruitmentSystem deserializeRecruitmentSystem(String json) {
        Gson gson = new GsonBuilder().registerTypeAdapter(User.class, new InterfaceAdapter<User>()).create();
        return gson.fromJson(json, RecruitmentSystem.class);
    }


    /**
     * Internal class, handing static classes
     *
     * @param <T>
     */
    final class InterfaceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

        public JsonElement serialize(T object, Type interfaceType, JsonSerializationContext context) {
            final JsonObject wrapper = new JsonObject();
            wrapper.addProperty("type", object.getClass().getName());
            wrapper.add("data", context.serialize(object));
            return wrapper;
        }

        public T deserialize(JsonElement elem, Type interfaceType, JsonDeserializationContext context) throws JsonParseException {
            final JsonObject wrapper = (JsonObject) elem;
            final JsonElement typeName = get(wrapper, "type");
            final JsonElement data = get(wrapper, "data");
            final Type actualType = typeForName(typeName);
            return context.deserialize(data, actualType);
        }

        private Type typeForName(final JsonElement typeElem) {
            try {
                return Class.forName(typeElem.getAsString());
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
        }

        private JsonElement get(final JsonObject wrapper, String memberName) {
            final JsonElement elem = wrapper.get(memberName);
            if (elem == null)
                throw new JsonParseException("no '" + memberName + "' member found in what was expected to be an interface wrapper");
            return elem;
        }
    }

}
