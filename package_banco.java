abstract public class Conta {

    protected String numero;
    protected double saldo;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getSaldo() {
        return saldo;
    }

    protected void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    abstract void creditar(double valor);

    abstract void debitar(double valor);
}


public class ContaNormal extends Conta {

    @Override
    public void creditar(double valor) {
        saldo += valor;
    }

    @Override
    public void debitar(double valor) {
        if (saldo >= valor) {
            saldo -= valor;
        } else {
            System.out.println("Saldo insuficiente para débito.");
        }
    }
}


abstract public class ContaEspecial extends Conta {

    protected double limite;

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    @Override
    public void creditar(double valor) {
        saldo += valor;
    }
}


public class ContaDebEspecial extends ContaEspecial {

    public ContaDebEspecial() {}

    public ContaDebEspecial(String numero, double saldo, double limite) {
        this.numero = numero;
        this.saldo = saldo;
        this.limite = limite;
    }

    @Override
    public void debitar(double valor) {
        if ((saldo + limite) >= valor) {
            saldo -= valor;
        } else {
            System.out.println("Limite insuficiente para débito.");
        }
    }
}


import java.util.ArrayList;
import java.util.List;

public class Banco {

    public static void main(String[] args) {

        List<Conta> contas = new ArrayList<>();

        ContaNormal conta1 = new ContaNormal();
        conta1.setNumero("1654-3");
        conta1.setSaldo(500);

        ContaDebEspecial conta2 = new ContaDebEspecial("4067-6", 2500, 1000.67);
        ContaDebEspecial conta3 = new ContaDebEspecial("5678-9", 3000, 5000);

        contas.add(conta1);
        contas.add(conta2);
        contas.add(conta3);

        realizarOperacoes(contas);
    }

    private static void realizarOperacoes(List<Conta> contas) {
        for (Conta conta : contas) {
            System.out.println("Conta número " + conta.getNumero() + " com saldo inicial R$ " + conta.getSaldo());

            conta.creditar(1000);
            System.out.println("Após crédito de R$ 1000,00, saldo: R$ " + conta.getSaldo());

            conta.debitar(100);
            System.out.println("Após débito de R$ 100,00, saldo: R$ " + conta.getSaldo());

            if (conta instanceof ContaDebEspecial) {
                ContaDebEspecial contaEspecial = (ContaDebEspecial) conta;
                System.out.println("Limite atual: R$ " + contaEspecial.getLimite());
                contaEspecial.setLimite(contaEspecial.getLimite() + 500);
                System.out.println("Novo limite após ajuste: R$ " + contaEspecial.getLimite());
            }
            System.out.println();
        }
    }
}
