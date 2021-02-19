package br.com.codigojava.mockbank.entity.enums;

public enum TipoConta {
    CONTA_CORRENTE_PF, CONTA_CORRENTE_PJ, POUPANCA;
    
    public static TipoConta getTipoConta(String tipoConta) {
	for (TipoConta tipo : TipoConta.values()) {
	    if(tipo.toString().contentEquals(tipoConta)) {
		return tipo;
	    }
	}
	return null;
    }
}
