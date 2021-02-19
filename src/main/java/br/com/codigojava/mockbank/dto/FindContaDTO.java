package br.com.codigojava.mockbank.dto;

import lombok.Data;

@Data
public class FindContaDTO {

    private Long numberAccount;
    private Long bankBranch;
    private String accountType;
    
}
