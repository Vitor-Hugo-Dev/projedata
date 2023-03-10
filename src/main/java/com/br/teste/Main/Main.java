package com.br.teste.Main;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.br.teste.Comparable.ComparadorNome;
import com.br.teste.Funcionario.Funcionario;

public class Main {
  private File arquivo = new File("src/Data/funcionarios.csv");

  private Scanner scan = null;

  private List<Funcionario> funcionarios = new ArrayList<Funcionario>();

  private Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<String, List<Funcionario>>();

  public void popularLista() throws IOException {
    try {
      scan = new Scanner(arquivo);
      while (scan.hasNextLine()) {

        String linha = scan.nextLine();

        String[] dados = linha.split(";");

        String nome = dados[0];

        String[] data = dados[1].split("/");
        int dia = Integer.parseInt(data[0]);
        int mes = Integer.parseInt(data[1]);
        int ano = Integer.parseInt(data[2]);
        LocalDate dataNascimento = LocalDate.of(ano, mes, dia);

        BigDecimal salario = new BigDecimal(dados[2]);

        String cargo = dados[3];

        Funcionario funcionario = new Funcionario(nome, dataNascimento, salario, cargo);
        funcionarios.add(funcionario);
      }

    } catch (IOException e) {
      throw e;
    } finally {
      if (Objects.nonNull(scan)) {
        scan.close();
      }
    }
  }

  public void removePorNome(String nome) {

    var funcionario = funcionarios.stream().filter((func) -> func.getNome().equals(nome)).collect(Collectors.toList());

    int index = funcionarios.indexOf(funcionario.get(0));

    funcionarios.remove(index);
    System.out.println("-----------------------------------------------------------------------------");
    System.out.println("O funcionario " + nome + " foi removido da lista.");
    System.out.println("-----------------------------------------------------------------------------");
  }

  public void aumentoSalario() {
    BigDecimal aumento = new BigDecimal("1.10");

    for (Funcionario func : funcionarios) {
      var salario = func.getSalario();

      BigDecimal salarioComAumento = salario.multiply(aumento);
      func.setSalario(salarioComAumento);
    }
    System.out.println("-----------------------------------------------------------------------------");
    System.out.println("Seus funcionarios receberam um aumento de 10%.");
    System.out.println("-----------------------------------------------------------------------------");
  }

  public void imprimirFuncionarios() {
    DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt", "Brazil"));
    dfs.setDecimalSeparator(',');
    dfs.setGroupingSeparator('.');
    DecimalFormat formatoSalario = new DecimalFormat("#,##0.00", dfs);

    System.out.println("-----------------------------------------------------------------------------");
    System.out.printf("%15s %15s %15s %15s", "NOME", "DATA NASCIMENTO", "SALARIO", "FUN????O");
    System.out.println();
    System.out.println("-----------------------------------------------------------------------------");
    System.out.println();

    for (Funcionario func : funcionarios) {
      String nome = func.getNome();
      String data = formatoData.format(func.getDataNascimento());

      BigDecimal salario = func.getSalario();
      String salarioFinal = formatoSalario.format(salario);

      String funcao = func.getFuncao();
      System.out.format("%15s %15s %15s %15s", nome, data, salarioFinal, funcao);
      System.out.println();
    }

  }

  public void agruparPorFuncao() {
    List<Funcionario> operadores = new ArrayList<Funcionario>();
    List<Funcionario> coordenadores = new ArrayList<Funcionario>();
    List<Funcionario> diretores = new ArrayList<Funcionario>();
    List<Funcionario> recepcionistas = new ArrayList<Funcionario>();
    List<Funcionario> contadores = new ArrayList<Funcionario>();
    List<Funcionario> gerentes = new ArrayList<Funcionario>();
    List<Funcionario> eletricistas = new ArrayList<Funcionario>();

    for (Funcionario func : funcionarios) {
      String funcao = func.getFuncao();

      switch (funcao) {
        case "Operador":
          operadores.add(func);
          break;
        case "Coordenador":
          coordenadores.add(func);
          break;
        case "Diretor":
          diretores.add(func);
          break;
        case "Recepcionista":
          recepcionistas.add(func);
          break;
        case "Contador":
          contadores.add(func);
          break;
        case "Eletricista":
          eletricistas.add(func);
          break;
        case "Gerente":
          gerentes.add(func);
          break;
      }
    }
    funcionariosPorFuncao.put("Operador", operadores);
    funcionariosPorFuncao.put("Coordenador", coordenadores);
    funcionariosPorFuncao.put("Diretor", diretores);
    funcionariosPorFuncao.put("Recepcionista", recepcionistas);
    funcionariosPorFuncao.put("Contador", contadores);
    funcionariosPorFuncao.put("Eletricista", eletricistas);
    funcionariosPorFuncao.put("Gerente", gerentes);
  }

  public void imprimePorFuncao() {
      funcionariosPorFuncao.values().forEach((var listFunc) -> {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%15s %15s %15s %15s", "NOME", "DATA NASCIMENTO", "SALARIO", "FUN????O");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println();

        listFunc.forEach((var func) -> {
          String nome = func.getNome();
          DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
          String data = formatoData.format(func.getDataNascimento());

          BigDecimal salario = func.getSalario();

          DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt", "Brazil"));
          dfs.setDecimalSeparator(',');
          dfs.setGroupingSeparator('.');
          DecimalFormat formatoSalario = new DecimalFormat("#,##0.00", dfs);
          String salarioFinal = formatoSalario.format(salario);

          String funcao = func.getFuncao();
          System.out.format("%15s %15s %15s %15s", nome, data, salarioFinal, funcao);
          System.out.println();
        });

      });

  }

  public void imprimirFuncionariosPorAniversario(List<Month> meses) {
    DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt", "Brazil"));
    dfs.setDecimalSeparator(',');
    dfs.setGroupingSeparator('.');
    DecimalFormat formatoSalario = new DecimalFormat("#,##0.00", dfs);

    System.out.println("-----------------------------------------------------------------------------");
    System.out.printf("%15s %15s %15s %15s", "NOME", "DATA NASCIMENTO", "SALARIO", "FUN????O");
    System.out.println();
    System.out.println("-----------------------------------------------------------------------------");
    System.out.println();

    for (Funcionario func : funcionarios) {
      if (meses.contains(func.getDataNascimento().getMonth())) {
        String nome = func.getNome();
        String data = formatoData.format(func.getDataNascimento());
        BigDecimal salario = func.getSalario();
        String salarioFinal = formatoSalario.format(salario);
        String funcao = func.getFuncao();
        System.out.format("%15s %15s %15s %15s", nome, data, salarioFinal, funcao);
        System.out.println();
      }
    }
  }

  public void imprimirFuncionarioDeMaisIdade() {
    var nome = "";
    var idade = 0;

    for (var index = 0; index <= funcionarios.size() -1; index ++) {
      if(idade < LocalDate.now().compareTo(funcionarios.get(index).getDataNascimento())) {
        nome = funcionarios.get(index).getNome();
        idade = LocalDate.now().compareTo(funcionarios.get(index).getDataNascimento());
      }
    }
    System.out.println("-----------------------------------------------------------------------------");
    System.out.printf("%15s %15s", "NOME", "IDADE");
    System.out.println();
    System.out.println("-----------------------------------------------------------------------------");
    System.out.format("%15s %15s", nome, idade);
    System.out.println();
    System.out.println("-----------------------------------------------------------------------------");

  }

  public void imprimirPorOrdemAlfabetica() {
    funcionarios.sort(new ComparadorNome());
    imprimirFuncionarios();
  }

  public void imprimeTotalDeSalarios() {
    var total = funcionarios.stream().map(Funcionario::getSalario)
    .reduce(BigDecimal.ZERO, BigDecimal::add);

    DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt", "Brazil"));
    dfs.setDecimalSeparator(',');
    dfs.setGroupingSeparator('.');
    DecimalFormat formatoSalario = new DecimalFormat("#,##0.00", dfs);
    String salarioTotal = formatoSalario.format(total);

    System.out.println("-----------------------------------------------------------------------------");
    System.out.println("SALARIO TOTAL DOS FUNCIONARIOS: " + salarioTotal);
    System.out.println("-----------------------------------------------------------------------------");
  }

  public void imprimeQtdSalarioMinimoPorFuncionario() {
    System.out.println("-----------------------------------------------------------------------------");
    System.out.println("SALARIOS M??NIMOS POR FUNCIONARIO.");
    System.out.println("-----------------------------------------------------------------------------");

    for (Funcionario func : funcionarios) {
      String nome = func.getNome();
      final BigDecimal salarioMinimo = new BigDecimal("1212.00"); 
      BigDecimal salariosPorFunc = func.getSalario().divideToIntegralValue(salarioMinimo);

      System.out.format("%15s %15s", nome, salariosPorFunc);
      System.out.println();
    }
    System.out.println("-----------------------------------------------------------------------------");
  }
}
