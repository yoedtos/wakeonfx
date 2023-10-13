package net.yoedtos.wakeonfx.repository.fs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.yoedtos.wakeonfx.exceptions.FSException;
import net.yoedtos.wakeonfx.model.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

public class HostsFileHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(HostsFileHandler.class);

    private final Path jsonFile;

    public HostsFileHandler(Path jsonFile) {
        this.jsonFile = jsonFile;
    }

    public void write(List hosts) throws FSException {
        try {
            var jsonString = new Gson().toJson(hosts, List.class);
            Files.writeString(jsonFile, jsonString);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new FSException(e.getMessage());
        }
    }

    public List<Host> read() throws FSException {
        List<Host> hosts;
        try {
            var jsonString = Files.readString(jsonFile);
            Type typeOf = TypeToken.getParameterized(List.class, Host.class).getType();
            hosts = new Gson().fromJson(jsonString, typeOf);
        } catch (NoSuchFileException e) {
            return List.of();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new FSException(e.getMessage());
        }
        return hosts;
    }
}
