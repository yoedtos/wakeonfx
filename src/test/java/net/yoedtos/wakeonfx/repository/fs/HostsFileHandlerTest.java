package net.yoedtos.wakeonfx.repository.fs;

import static net.yoedtos.wakeonfx.util.Constants.HOSTS_FILE;
import static net.yoedtos.wakeonfx.util.TestConstants.JSON_HOSTS;
import static net.yoedtos.wakeonfx.util.TestDataSet.createSecureOnHost;
import static net.yoedtos.wakeonfx.util.TestDataSet.createSimpleHost;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import net.yoedtos.wakeonfx.exceptions.FSException;
import net.yoedtos.wakeonfx.model.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

class HostsFileHandlerTest {

    private Path jsonFile;
    private HostsFileHandler handler;
    private List<Host> hosts;

    @BeforeEach
    void create() {
        Path testDir = Jimfs.newFileSystem(Configuration.forCurrentPlatform())
                        .getPath(".");
        jsonFile = testDir.resolve(HOSTS_FILE);
        hosts = Arrays.asList(createSimpleHost(), createSecureOnHost());
    }

    @Test
    void testWriteJsonFile() throws FSException, IOException {
        Files.createFile(jsonFile);
        handler = new HostsFileHandler(jsonFile);
        handler.write(hosts);
        String json = Files.readString(jsonFile);
        assertThat(json).isEqualTo(JSON_HOSTS);
    }

    @Test
    void testReadJsonFile() throws IOException, FSException {
        Files.createFile(jsonFile);
        Files.writeString(jsonFile, JSON_HOSTS);
        handler = new HostsFileHandler(jsonFile);
        List<Host> objects = handler.read();
        assertThat(objects)
                .hasSize(2)
                .isEqualTo(hosts);
    }

    @Test
    void whenJsonFileNotExistShouldHaveZeroHost() throws FSException {
        handler = new HostsFileHandler(jsonFile);
        List<Host> objects = handler.read();
        assertThat(objects).isEmpty();
    }
}
