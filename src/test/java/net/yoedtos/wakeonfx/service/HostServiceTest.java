package net.yoedtos.wakeonfx.service;

import static net.yoedtos.wakeonfx.util.TestConstants.*;
import static net.yoedtos.wakeonfx.util.TestDataSet.createSecureOnHost;
import static net.yoedtos.wakeonfx.util.TestDataSet.createSimpleHost;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

import net.yoedtos.wakeonfx.control.Index;
import net.yoedtos.wakeonfx.exceptions.RepositoryException;
import net.yoedtos.wakeonfx.exceptions.ServiceException;
import net.yoedtos.wakeonfx.model.Address;
import net.yoedtos.wakeonfx.model.Host;
import net.yoedtos.wakeonfx.repository.HostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class HostServiceTest {

    @Mock
    private HostRepository mockHostRepo;
    @Captor
    private ArgumentCaptor<Integer> captor;
    @InjectMocks
    private HostService hostService;

    private Host hostOne;
    private Index indexOne;

    @BeforeEach
    public void init() {
        hostOne = createSimpleHost();
        indexOne = new Index(ID_ONE, SIMPLE_HOST);
    }

    @Test
    void whenCreateShouldReturnIndex() throws RepositoryException, ServiceException {
        when(mockHostRepo.persist(any())).thenReturn(ID_ONE);
        var result = hostService.create(hostOne);
        assertThat(result).isEqualTo(indexOne);
        verify(mockHostRepo, times(1)).persist(hostOne);
    }

    @Test
    void whenCreateFailShouldThrowsException() throws RepositoryException {
        doThrow(new RepositoryException()).when(mockHostRepo).persist(hostOne);
        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> hostService.create(hostOne));
    }

    @Test
    void whenGetShouldReturnHost() throws RepositoryException, ServiceException {
        when(mockHostRepo.findById(ID_ONE)).thenReturn(hostOne);
        var result = hostService.get(ID_ONE);
        assertThat(result).isEqualTo(hostOne);
        verify(mockHostRepo, times(1)).findById(ID_ONE);
    }

    @Test
    void whenRemoveShouldReturnHostIndex() throws RepositoryException, ServiceException {
        hostService.remove(ID_ONE);
        verify(mockHostRepo).remove(captor.capture());
        assertThat(captor.getValue()).isEqualTo(ID_ONE);
        verify(mockHostRepo, times(1)).remove(ID_ONE);
    }

    @Test
    void whenGetIndicesShouldReturnTwoIndex() throws RepositoryException, ServiceException {
        Host hostTwo = createSecureOnHost();
        when(mockHostRepo.findAll()).thenReturn(Arrays.asList(hostOne, hostTwo));
        var results = hostService.getIndices();
        assertThat(results)
                .hasSize(2)
                .contains(indexOne, new Index(ID_TWO, SECURE_HOST));
    }

    @Test
    void whenModifyShouldReturnModIndex() throws ServiceException, RepositoryException {
        Address address =  new Address(IP_ADD_MOD, MAC_ADD_MOD, null);
        Host hostMod = new Host(MOD_HOST, PORT_NUM_MOD, TIME_OUT_MOD, address);
        when(mockHostRepo.merge(hostMod)).thenReturn(ID_ONE);
        var result = hostService.modify(hostMod);
        verify(mockHostRepo, times(1)).merge(hostMod);
        assertThat(result).isEqualTo(new Index(ID_ONE, MOD_HOST));
    }
}
