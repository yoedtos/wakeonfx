package net.yoedtos.wakeonfx.repository;

import static net.yoedtos.wakeonfx.util.TestDataSet.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import net.yoedtos.wakeonfx.exceptions.FSException;
import net.yoedtos.wakeonfx.exceptions.RepositoryException;
import net.yoedtos.wakeonfx.model.Host;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HostRepositoryTest {

    @Mock
    private Cache cache;

    @InjectMocks
    private HostRepository hostRepository;

    private Host hostOne;
    private Host hostTwo;

    @BeforeEach
    void init() {
        hostOne = createSimpleHost();
        hostTwo = createSecureOnHost();
    }

    @Test
    @Order(1)
    void whenPersistSuccessShouldReturnZero() throws RepositoryException, FSException {
        var id = hostRepository.persist(hostOne);
        assertThat(id).isEqualTo(0);
        verify(cache).update(List.of(hostOne));
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
    void whenRemoveSuccessShouldBeNull() throws RepositoryException, FSException {
        hostRepository.remove(0);
        var host = hostRepository.findById(0);
        assertThat(host).isNull();
        verify(cache, times(1)).update(List.of(hostTwo));
    }

    @Test
    @Order(6)
    void whenAddAfterRemoveShouldCacheCorrectly() throws RepositoryException, FSException {
        hostRepository.remove(1);
        verify(cache).update(List.of());

        var id = hostRepository.persist(hostOne);
        assertThat(id).isEqualTo(2);
        verify(cache).update(List.of(hostOne));
    }

    @Test
    @Order(7)
    void whenRemoveAgainShouldCacheCorrectly() throws RepositoryException, FSException {
        hostRepository.remove(2);
        verify(cache).update(List.of());
    }
}
