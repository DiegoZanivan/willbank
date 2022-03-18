package com.will.bank.mocky;

import com.will.bank.model.SaldoDto;
import com.will.bank.model.TransacaoDto;
import com.will.bank.model.WillBank;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface MockyApi {

    @GET("v3/c3bdfbf6-d789-4e52-829c-bddbb886c3be")
    Call<List<TransacaoDto>> getTransacoes();

    @GET("v3/e0f453b7-620c-4479-839e-28ac58111fca")
    Call<SaldoDto> getSaldo();

    @GET("v3/85c286b6-e483-420f-9f2b-1ca57ae06c52")
    Call<List<WillBank>> getWillbank();
}
