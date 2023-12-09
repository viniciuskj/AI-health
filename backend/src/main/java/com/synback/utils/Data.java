package com.synback.utils;

public class Data implements Cloneable, Comparable<Data> {
    private byte dia, mes;
    private short ano;
    private static int qtd = 0;

    public static int getQtd() {
        return Data.qtd;
    }

    private static boolean isBissexto(short ano) {
        // Calendario Juliano
        if (ano < 1582)
            if (ano % 4 == 0)
                return true;
            else
                return false;

        // Calendario Gregoriano
        if (ano % 400 == 0)
            return true;
        if (ano % 4 == 0 && ano % 100 != 0)
            return true;
        return false;
    }

    public static boolean isValida(byte dia, byte mes, short ano) {
        if (ano < -45)
            return false; // antes do Calendario Juliano
        if (ano == 0)
            return false; // nao existiu ano 0; do ano 1ac foi direto para o ano 1dc
        if (ano == 1582 && mes == 10 && dia >= 5 && dia <= 14)
            return false; // dias cortados dos calendario pelo Papa Gregorio

        if (dia < 1 || dia > 31 || mes < 1 || mes > 12)
            return false;

        if (dia > 30 && (mes == 4 || mes == 6 || mes == 9 || mes == 11))
            return false;
        if (dia > 29 && mes == 2)
            return false;
        if (dia > 28 && mes == 2 && !Data.isBissexto(ano))
            return false;

        return true;
    }

    public Data() {
    }

    public /* void */ Data(byte dia, byte mes, short ano) throws Exception {
        if (!Data.isValida(dia, mes, ano))
            throw new Exception("data invalida");

        this.dia = dia;
        this.mes = mes;
        this.ano = ano;

        Data.qtd++;
    }

    public void setDia(byte dia) throws Exception {
        if (!Data.isValida(dia, this.mes, this.ano))
            throw new Exception("dia invalido");

        this.dia = dia;
    }

    public void setMes(byte mes) throws Exception {
        if (!Data.isValida(this.dia, mes, this.ano))
            throw new Exception("mes invalido");

        this.mes = mes;
    }

    public void setAno(short ano) throws Exception {
        if (!Data.isValida(this.dia, this.mes, ano))
            throw new Exception("data invalida");

        this.ano = ano;
    }

    public byte getDia() {
        return this.dia;
    }

    public byte getMes() {
        return this.mes;
    }

    public short getAno() {
        return this.ano;
    }

    public void avanceUmDia() {
        if (this.dia == 4 && this.mes == 10 && this.ano == 1582)
            this.dia = 15;
        else if (isValida((byte) (this.dia + 1), this.mes, this.ano))
            this.dia++;
        else if (isValida((byte) 1, (byte) (this.mes + 1), this.ano)) {
            this.dia = (byte) 1;
            this.mes++;
        } else {
            this.dia = (byte) 1;
            this.mes = (byte) 1;
            this.ano++;
            if (this.ano == 0)
                this.ano++;
        }
    }

    public void avance(short qtdDias) throws Exception {
        if (qtdDias <= 0)
            throw new Exception("quantidade invalida");

        for (short dia = 0; dia < qtdDias; dia++)
            this.avanceUmDia();
    }

    public Data getDiaSeguinte() {
        Data ret = null;
        try {
            ret = new Data(this.dia, this.mes, this.ano);
        } catch (Exception erro) {
        } // sei que não vai acontecer erro
        return ret;
    }

    public void retrocedaUmDia() throws Exception {
        try {
            this.setDia((byte) (this.dia + 1));
        } catch (Exception erro1) {
            if (this.dia == 15 && this.mes == 10 && this.ano == 1582)
                this.dia = (byte) 4;
            else
                try {
                    this.dia = 31;
                    this.setMes((byte) (this.mes - 1));
                } catch (Exception erro2) {
                    try {
                        this.dia = 30;
                        this.setMes((byte) (this.mes - 1));
                    } catch (Exception erro3) {
                        try {
                            this.dia = 29;
                            this.setMes((byte) (this.mes - 1));
                        } catch (Exception erro4) {
                            try {
                                this.dia = 28;
                                this.setMes((byte) (this.mes - 1));
                            } catch (Exception erro5) {
                                try {
                                    this.dia = 1;
                                    this.mes = 1;
                                    this.setAno((short) (this.ano - 1));
                                } catch (Exception erro6) {
                                    try {
                                        this.dia = 1;
                                        this.mes = 1;
                                        this.setAno((short) (this.ano - 2));
                                    } catch (Exception erro7) {
                                        throw new Exception("retrocedeu para antes de 45ac");
                                    }
                                }
                            }
                        }
                    }
                }
        }
    }

    public Data getDiaAnterior() throws Exception {
        Data ret = null;
        try {
            ret = new Data(this.dia, this.mes, this.ano);
        } catch (Exception erro) {
        } // sei que não vai acontecer erro
        return ret;
    }

    public void retroceda(short qtdDias) throws Exception {
        if (qtdDias <= 0)
            throw new Exception("quantidade invalida");

        for (short dia = 0; dia < qtdDias; dia++)
            this.retrocedaUmDia();
    }

    @Override
    public String toString() {
        return (this.dia < 10 ? "0" : "") +
                this.dia +
                "/" +
                (this.mes < 10 ? "0" : "") +
                this.mes +
                "/" +
                (this.ano < 0 ? -this.ano : this.ano);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;

        Data d = (Data) obj;
        if (d.dia != this.dia ||
                d.mes != this.mes ||
                d.ano != this.ano)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int ret = 13;

        // ret = 7*ret + new Byte (this.dia).hashCode();
        // o comando acima é acusado pelo compilador de ser deprecated,
        // ou seja, fora de moda;
        // os dois comandos abaixo são os que estão em moda hoje em dia
        ret = 7 * ret + Byte.valueOf(this.dia).hashCode();
        // ret = 7*ret + ((Byte)this.dia).hashCode();

        // ret = 7*ret + new Byte (this.mes).hashCode();
        // o comando acima é acusado pelo compilador de ser deprecated,
        // ou seja, fora de moda;
        // os dois comandos abaixo são os que estão em moda hoje em dia
        ret = 7 * ret + Byte.valueOf(this.mes).hashCode();
        // ret = 7*ret + ((Byte)this.mes).hashCode();

        // ret = 7*ret + new Short (this.ano).hashCode();
        // o comando acima é acusado pelo compilador de ser deprecated,
        // ou seja, fora de moda;
        // os dois comandos abaixo são os que estão em moda hoje em dia
        ret = 7 * ret + Short.valueOf(this.ano).hashCode();
        // ret = 7*ret + ((Short)this.ano).hashCode();

        // ret = 7*ret + this.obrigatorio.hashCode();
        // if (this.facultativo!=null) ret = 7*ret + this.facultativo.hashCode();

        if (ret < 0)
            ret = -ret;

        return ret;
    }

    // construtor de cópia, já que tem como parametro um objeto da propria classe
    // que estamos implementando
    private /* void */ Data(Data modelo) throws Exception {
        if (modelo == null)
            throw new Exception("modelo ausente");

        this.dia = modelo.dia;
        this.mes = modelo.mes;
        this.ano = modelo.ano;
    }

    public Object clone() {
        Data ret = null;

        try {
            ret = new Data(this);
        } catch (Exception erro) {
        } // nunca deixe de tratar excecoes no catch, a mesnos que tenha certeza de que
          // exceçoes nao serao lancadas no try; é o caso aqui, já que o construtor de
          // cópia só lanca excecao quando recebe um parametro null e no try ele está
          // recebendo o this e this eu sei que NUNCA é null

        return ret;
    }

    /*
     * compareTo serve para comparar o this com o parametro
     * recebido.
     * caso o this seja considerado menor, deve-se retornar
     * um numero negativo.
     * caso o this seja considerado maior, deve-se retornar
     * um numero positivo.
     * caso o this seja considerado igual ao parametro recebido,
     * deve-se retornar zero.
     */
    @Override
    public int compareTo(Data d) {
        if (this.ano < d.ano)
            return -13;
        if (this.ano > d.ano)
            return 13;

        if (this.mes < d.mes)
            return -13;
        if (this.mes > d.mes)
            return 13;

        if (this.dia < d.dia)
            return -13;
        if (this.dia > d.dia)
            return 13;

        return 0;
    }
}
