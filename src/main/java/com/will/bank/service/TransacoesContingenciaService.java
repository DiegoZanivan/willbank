package com.will.bank.service;

import com.will.bank.error.BusinessException;
import com.will.bank.error.EntityNotFoundException;
import com.will.bank.mocky.MockyApi;
import com.will.bank.model.ClienteDto;
import com.will.bank.model.SaldoDto;
import com.will.bank.model.TransacaoDto;
import com.will.bank.model.WillBank;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransacoesContingenciaService {

    private MockyApi api;

    public TransacoesContingenciaService() {
        buildRetrofit();
    }

    public void reenviarPorEmail(String email) {
        try {
            List<TransacaoDto> transacoes = getTransacoes();
            TransacaoDto transacaoDto = transacoes.stream().filter(t -> email.equals(t.getEmail()))
                    .findFirst()
                    .orElse(null);

            if (transacaoDto == null) {
                throw new EntityNotFoundException("Cliente não encontrado");
            }

            List<WillBank> banks = getClientes();
            if (banks.isEmpty()) {
                throw new BusinessException("Falha ao buscar clientes");
            }

            Optional<ClienteDto> clienteDto = banks.stream()
                    .map(bank -> bank.getCustomers().stream()
                            .filter(c -> c.getCustomerID().equals(transacaoDto.getCustomerID()))
                            .findFirst()
                            .orElse(null)
                    ).findFirst();

            if (clienteDto.isEmpty()) {
                throw new BusinessException("Cliente não encontrado");
            }

            SaldoDto saldo = getSaldo();

            if (saldo.getBalance().compareTo(transacaoDto.getValue()) == -1) {
                throw new BusinessException("Cliente não tem saldo suficiente para esta transação.", HttpStatus.PRECONDITION_FAILED);
            }
            reenviar(transacaoDto, clienteDto.get());

        } catch (IOException e) {
            throw new BusinessException("Falha ao conectar com nossos servidores.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public SaldoDto getSaldo() throws IOException {
        return api.getSaldo().execute().body();
    }

    public List<TransacaoDto> getTransacoes() throws IOException {
        return api.getTransacoes().execute().body();
    }

    public List<WillBank> getClientes() throws IOException {
        return api.getWillbank().execute().body();
    }

    public void reenviar(TransacaoDto transacao, ClienteDto clienteDto) {
        clienteDto.getAccount();
        transacao.getKey();
        // Reenvia
    }

    protected void buildRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://run.mocky.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(MockyApi.class);
    }
}
