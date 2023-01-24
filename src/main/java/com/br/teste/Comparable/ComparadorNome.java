package com.br.teste.Comparable;

import java.util.Comparator;

import com.br.teste.Funcionario.Funcionario;

public class ComparadorNome implements Comparator<Funcionario>{

  @Override
  public int compare(Funcionario o1, Funcionario o2) {
    String nome1 = o1.getNome();
    String nome2 = o2.getNome();

    return nome1.compareTo(nome2);
  }
  
}
