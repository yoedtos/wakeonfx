package net.yoedtos.wakeonfx.repository;

import static net.yoedtos.wakeonfx.util.TestDataSet.*;
import static org.assertj.core.api.Assertions.assertThat;

import net.yoedtos.wakeonfx.exceptions.RepositoryException;
import net.yoedtos.wakeonfx.model.Host;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HostRepositoryTest {

    @Mock
    private Cache cache;

    @InjectMocks
    private HostRepository hostRepository;

    private Host hostOne;

    @BeforeEach
    void init() {
        hostOne = createSimpleHost();
    }

    @Test
    @Order(1)
    void whenPersistSuccessShouldReturnZero() throws RepositoryException {
        var id = hostRepository.persist(hostOne);
        assertThat(id).isEqualTo(0);
    }

    @Test
    @Order(2)
    void whenFindBySuccessShouldReturnOne() throws RepositoryException {
        var host = hostRepository.findById(0);
        assertThat(host).isEqualTo(hostOne);
    }

    @Test
    @Order(3)
    void whenFindAllSuccessShouldReturnTwo() throws RepositoryException {
        Host hostTwo = createSecureOnHost();
        hostRepository.persist(hostTwo);
        var hosts = hostRepository.findAll();
        assertThat(hosts).hasSize(2).contains(hostOne, hostTwo);
    }

    @Test
    @Order(4)
    void whenMergeSuccessShouldReturnZero() throws RepositoryException {
        Host hostOneMod = createSimpleHostMod();
        var id = hostRepository.merge(hostOneMod);
        assertThat(id).isEqualTo(0);
        var host = hostRepository.findById(0);
        assertThat(host).isEqualTo(hostOneMod);
    }

    @Test
    @Order(5)
    void whenRemoveSuccessShouldBeNull() throws RepositoryException {
        hostRepository.remove(0);
        var host = hostRepository.findById(0);
        assertThat(host).isNull();
    }
}
