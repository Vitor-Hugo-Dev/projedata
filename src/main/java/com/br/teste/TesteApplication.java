package com.br.teste;

import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.br.teste.Main.Main;

@SpringBootApplication
public class TesteApplication {

	public static Main teste = new Main();
	public static void main(String[] args) throws IOException {
		SpringApplication.run(TesteApplication.class, args);
		teste.popularLista();
		teste.removePorNome("João");
		teste.imprimirFuncionarios();
		teste.aumentoSalario();
		teste.agruparPorFuncao();
		teste.imprimePorFuncao();
		var meses = new ArrayList<Month>();
		meses.add(Month.OCTOBER);
		meses.add(Month.DECEMBER);
		teste.imprimirFuncionariosPorAniversario(meses);
		teste.imprimirFuncionarioDeMaisIdade();
		teste.imprimirPorOrdemAlfabetica();
		teste.imprimeTotalDeSalarios();
		teste.imprimeQtdSalarioMinimoPorFuncionario();
	}

}
