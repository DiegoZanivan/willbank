package com.will.bank.service;

import com.will.bank.error.BusinessException;
import com.will.bank.error.EntityNotFoundException;
import com.will.bank.model.ClienteDto;
import com.will.bank.model.SaldoDto;
import com.will.bank.model.TransacaoDto;
import com.will.bank.model.WillBank;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransacoesContingenciaServiceTest {

    TransacoesContingenciaService service;
    TransacaoDto transacaoDto;
    List<TransacaoDto> transacoes;
    List<WillBank> banks;
    List<ClienteDto> clientes;
    ClienteDto clienteDto;
    SaldoDto saldo;

    @Before
    public void before() {
        service = spy(new TransacoesContingenciaService());
    }

    @Test
    public void deveReenviarTransacaoQuandoClienteTemSaldoSuperior() throws IOException {
        preencheVariaveis();
        saldo.setBalance(new BigDecimal(5000));
        transacaoDto.setValue(new BigDecimal(100));

        doReturn(saldo).when(service).getSaldo();
        doReturn(transacoes).when(service).getTransacoes();
        doReturn(banks).when(service).getClientes();

        service.reenviarPorEmail("email");

        verify(service, times(1)).reenviar(any(), any());
    }

    @Test
    public void deveReenviarTransacaoQuandoClienteTemSaldoIgual() throws IOException {
        preencheVariaveis();
        saldo.setBalance(new BigDecimal(5000));
        transacaoDto.setValue(new BigDecimal(5000));

        doReturn(saldo).when(service).getSaldo();
        doReturn(transacoes).when(service).getTransacoes();
        doReturn(banks).when(service).getClientes();

        service.reenviarPorEmail("email");

        verify(service, times(1)).reenviar(any(), any());
    }

    @Test(expected = BusinessException.class)
    public void naoDeveReenviarTransacaoSeClienteNaoTemSaldo() throws IOException {
        preencheVariaveis();
        saldo.setBalance(new BigDecimal(100));
        transacaoDto.setValue(new BigDecimal(1000));

        doReturn(saldo).when(service).getSaldo();
        doReturn(banks).when(service).getClientes();
        doReturn(transacoes).when(service).getTransacoes();

        service.reenviarPorEmail("email");

        verify(service, times(0)).reenviar(any(), any());
    }

    @Test(expected = EntityNotFoundException.class)
    public void deveLancarExcecaoSeClienteNaoEncontrado() throws IOException {
        preencheVariaveis();
        saldo.setBalance(new BigDecimal(100));
        transacaoDto.setValue(new BigDecimal(1000));
        transacaoDto.setEmail("email_diferente");

        doReturn(transacoes).when(service).getTransacoes();

        service.reenviarPorEmail("email");

        fail("Não deve chegar neste ponto");
    }

    @Test(expected = BusinessException.class)
    public void deveLancarExcecaoSeFalharAlgumaChamada() throws IOException {
        SaldoDto saldo = new SaldoDto();
        saldo.setBalance(new BigDecimal(100));

        doThrow(IOException.class).when(service).getTransacoes();

        service.reenviarPorEmail("email");

        fail("Não deve chegar neste ponto");
    }

    private void preencheVariaveis() {
        saldo = new SaldoDto();
        UUID id = UUID.randomUUID();

        transacaoDto = new TransacaoDto();
        transacaoDto.setEmail("email");
        transacaoDto.setCustomerID(id);
        transacoes = Arrays.asList(transacaoDto);

        banks = new ArrayList<>();
        clienteDto = new ClienteDto();
        clienteDto.setCustomerID(id);
        clientes = new ArrayList<>();
        clientes.add(clienteDto);
        WillBank bank = new WillBank();
        bank.setCustomers(clientes);
        banks.add(bank);
    }
}
