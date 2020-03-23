package Auxiliar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Valor {

    private String stringValue;
    private String apelido = "";

    public Valor(String stringValue) {
        this.stringValue = stringValue;
    }

    public Valor(String stringValue, String apelido) {
        this.stringValue = stringValue;
        this.apelido = apelido;
    }

    public Valor(Integer integer) {
        try {
            stringValue = integer.toString();
        } catch (Exception e) {
            stringValue = "0";
        }
    }

    public Valor(Integer integer, String apelido) {
        try {
            stringValue = integer.toString();
        } catch (Exception e) {
            stringValue = "0";
        } finally {
            this.apelido = apelido;
        }
    }

    public Valor(Long longg) {
        try {
            stringValue = longg.toString();
        } catch (Exception e) {
            stringValue = "0";
        }
    }

    public Valor(Long longg, String apelido) {
        try {
            stringValue = longg.toString();
        } catch (Exception e) {
            stringValue = "0";
        } finally {
            this.apelido = apelido;
        }
    }

    public Valor(BigDecimal big) {
        try {
            stringValue = big.toString();
        } catch (Exception e) {
            stringValue = "0.0";
        }
    }

    public Valor(BigDecimal big, String apelido) {
        try {
            stringValue = big.toString();
        } catch (Exception e) {
            stringValue = "0.0";
        } finally {
            this.apelido = apelido;
        }
    }

    public Valor(Double dbl) {
        try {
            stringValue = dbl.toString();
        } catch (Exception e) {
            stringValue = "0.0";
        }
    }

    public Valor(Double dbl, String apelido) {
        try {
            stringValue = dbl.toString();
        } catch (Exception e) {
            stringValue = "0.0";
        } finally {
            this.apelido = apelido;
        }
    }
    /**
     * Copia string do valor. NÂO copia apelido.
     */
    public Valor(Valor vlr) {
        try {
            stringValue = vlr.getString();
        } catch (Exception e) {
            stringValue = "";
        }
    }
    
    /**
     * Copia string do valor. NÂO copia apelido, utiliza o apelido passado
     */
    public Valor(Valor vlr, String apelido) {
        try {
            stringValue = vlr.getString();
        } catch (Exception e) {
            stringValue = "";
        } finally {
            this.apelido = apelido;
        }
    }

    /**
     * Define a string como o formato yyyy-mm-dd do calendario
     */
    public Valor(Calendar cal) {
        try {
            stringValue = cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
        } catch (Exception e) {
            stringValue = "01/01/1900";
        }
    }

    /**
     * Define a string como o formato yyyy-mm-dd do calendario
     */
    public Valor(Calendar cal, String apelido) {
        try {
            stringValue = cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
        } catch (Exception e) {
            stringValue = "1900-01-01";
        } finally {
            this.apelido = apelido;
        }
    }

    public String getApelido() {
        return apelido;
    }

    public String getString() {
        return stringValue;
    }

    public void setString(String stringValue) {
        this.stringValue = stringValue;
    }

    /**
     * Copia string do valor. NÂO copia apelido.
     */
    public void set(Valor valor) {
        this.stringValue = valor.getString();
    }

    /**
     * @return Bigdecimal dividido por 100
     */
    public BigDecimal getPercent() {
        return new BigDecimal(getBigDecimal().divide(new BigDecimal("100")).toString());
    }

    public BigDecimal getBigDecimal() {
        try {
            String stringBigDecimal;
            if (stringValue.contains(",")) {
                stringBigDecimal = stringValue.replaceAll("\\.", "").replaceAll(",", "\\.");
            } else {
                stringBigDecimal = stringValue;
            }
            BigDecimal big = new BigDecimal(stringBigDecimal);
            big = big.setScale(2, RoundingMode.CEILING);

            return big;
        } catch (Exception e) {
            return new BigDecimal(0);
        }
    }

    /**
     * @return Currency (R$ 0,00) do Bigdecimal com 2 casas decimais
     */
    public String getMoney() {
        BigDecimal newBig = getBigDecimal().setScale(2, BigDecimal.ROUND_HALF_EVEN);
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(newBig);
    }

    public Double getDouble() {
        try {
            Double newDouble;
            if (stringValue.contains(",")) {
                newDouble = new Double(stringValue.replaceAll("\\.", "").replaceAll(",", "\\."));
            } else {
                newDouble = new Double(stringValue);
            }

            return roundDouble(newDouble, 2);
        } catch (Exception e) {
            return new Double(0);
        }
    }

    public Integer getInteger() {
        try {
            return new Integer(stringValue);
        } catch (Exception e) {
            return new Integer(0);
        }
    }

    /**
     * @return Retorna um integer de 1 a 12
     */
    public Integer getMes(){
        try {
            Integer intVal = getInteger();
            return intVal>=1 && intVal<=12?intVal:new Integer(1);
        } catch (Exception e) {
            return new Integer(1);
        }
    }
    
    public Long getLong() {
        try {
            return new Long(stringValue);
        } catch (Exception e) {
            return new Long(0);
        }
    }

    /**
     * @return Currency do valor ou 'R$ 0,00' para erros.
     */
    public String getMoeda() {
        try {
            return NumberFormat.getCurrencyInstance().format(getBigDecimal());
        } catch (Exception e) {
            return "R$ 0,00";
        }
    }

    /**
     * @return Se o valor for "nao", "false", "falso" ou e branco, retorna
     * FALSO, caso contrário retorna TRUE
     */
    public boolean getBoolean() {
        try {
            String str = stringValue.toLowerCase();
            str = str.replaceAll("ã", "a");

            return !(str.equals("nao") || str.equals("false") || str.equals("falso") || str.equals(""));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Formato considerado: Padrão "dmy" que busca os numero na ordem de dia,mes
     * e ano
     *
     * @return Variavel no formato Calendario.
     */
    public Calendar getCalendar() {
        return getCalendar("dmy");
    }

    /**
     * @param format Padrão "dmy" que busca os numero na ordem de dia, mes e ano
     * @return Variavel no formato Calendario.
     */
    public Calendar getCalendar(String format) {
        int date = 1;
        int month = 0;
        int year = 1900;

        Calendar cal = Calendar.getInstance();
        try {

            //Cria lista dos números em ordem
            List<String> nros = getNumbersList();

            //Identifica ordem
            String[] ordem = format.split("");
            if (ordem.length == nros.size()) {
                for (int i = 0; i < ordem.length; i++) {
                    String ord = ordem[i];
                    switch (ord) {
                        case "d":
                            date = Integer.valueOf(nros.get(i));
                            break;
                        case "y":
                            year = Integer.valueOf(nros.get(i));
                            break;
                        case "m":
                            month = Integer.valueOf(nros.get(i)) - 1;
                            break;
                        default:
                            break;
                    }
                }
            }
        } finally {
            cal.set(year, month, date, 0, 0, 0);
        }
        return cal;
    }

    /**
     * @return Data SQL (yyyy-mm-dd) de uma data em valor Inteiro. Ex: 20190131
     * --> '2019-01-31'
     */
    public String getIntegerToSql() {
        if (stringValue.length() == 8) {
            String ano = stringValue.substring(0, 4);
            String mes = stringValue.substring(4, 6);
            String dia = stringValue.substring(6, 8);

            return ano + "-" + mes + "-" + dia;
        } else {
            return "";
        }
    }

    /**
     * A data deve estar na ordem Dia->mes->ano, separados por algum caractere
     *
     * @return Numero inteiro com ano, mes e dia. Ex: 01/12/2019 --> 20190112
     */
    public Integer getNumberFromDateDMY() {
        List<String> numeros = getNumbersList();

        if (numeros.size() == 3) {

            //Integers
            int diaInt = Integer.valueOf(numeros.get(0));
            int mesInt = Integer.valueOf(numeros.get(1));
            int anoInt = Integer.valueOf(numeros.get(2));

            String dia = (diaInt < 10 ? "0" : "") + diaInt;
            String mes = (mesInt < 10 ? "0" : "") + diaInt;

            return Integer.valueOf(anoInt + mes + dia);
        } else {
            return 0;
        }
    }

    /**
     * Retorna data SQL do valor, consideranto o formato original como dmy
     *
     * @return Data no formato SQL(yyyy-mm-dd) do valor.
     */
    public String getSQLDate() {
        return getSQLDate("dmy");
    }

    /**
     * @param originalFormat Formato da data que o valor está EX: dmy --> dia,
     * mes e ano. Coloque apenas d,m ou y
     * @return Data no formato SQL(yyyy-mm-dd) do valor.
     */
    public String getSQLDate(String originalFormat) {
        Calendar cal = getCalendar(originalFormat);
        return getSQLDateFromCalendar(cal);
    }

    /**
     * @return Data no formato SQL(yyyy-mm-dd) de um calendario
     */
    public static String getSQLDateFromCalendar(Calendar cal) {
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @return Se o valor é uma data no formato dd/mm/yyyy
     */
    public boolean éUmaDataValida() {
        return éUmaDataValida("dd/mm/yyyy");
    }

    /**
     * @return Se o valor é uma data no formato informado
     */
    public boolean éUmaDataValida(String formato) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formato);
            format.setLenient(false);
            Date data = format.parse(stringValue);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * @return Lista de todos números(em texto) no valor
     */
    public List<String> getNumbersList() {
        return getNumbersList("");
    }

    /**
     * @param ignoredChars Caracteres que serão considerados juntos aos numeros
     * Ex: Caso o ignorado fosse "/" e o valor fosse "safaf5 554/88 sqf",
     * retornaria dois textos('5' e '554/88')
     * @return Lista de todos números(em texto) no valor
     */
    public List<String> getNumbersList(String ignoredChars) {
        List<String> numberList = new ArrayList<>();

        try {
            /*Filtro Regex*/
            String regex = "[0-9" + ignoredChars + "]";

            /*Separa caracteres*/
            String[] split = stringValue.split("");

            String nro = "";
            for (int i = 0; i <= split.length; i++) {
                String charr = split.length != i ? split[i] : "";
                if (charr.matches(regex)) {
                    nro += charr;
                } else if (!nro.equals("") && !nro.equals(ignoredChars)) {
                    numberList.add(nro);
                    nro = "";
                }
            }
        } catch (Exception e) {
        }

        return numberList;
    }

    /**
     * @param value Valor double do numero
     * @param places Quantidade de casas decimais após a virgula
     * @return classe Double com número arredondado.
     */
    public static Double roundDouble(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
}
