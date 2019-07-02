import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class CalculoPrazo {
	public Timestamp dataFinal(int prazo) {
		
		Calendar momentoAtual = Calendar.getInstance();
		int qtdDiasDoPrazo = (prazo / 9);
		int tempoRestanteDaQtdDiasDoPrazo = (prazo % 9);
		Timestamp prazoFinal;

		verificarHorarioUtil(momentoAtual);
		verificarFimDeSemana(momentoAtual);

		if (qtdDiasDoPrazo == 0) {
			adicionaXHoras(momentoAtual, prazo);
			verificarHorarioUtil(momentoAtual);
			verificarFimDeSemana(momentoAtual);
			prazoFinal = new Timestamp(momentoAtual.getTimeInMillis());
		} else {
			while (qtdDiasDoPrazo > 0) {
				adicionaXHoras(momentoAtual, 24);
				verificarHorarioUtil(momentoAtual);
				verificarFimDeSemana(momentoAtual);
				qtdDiasDoPrazo--;
			}
			adicionaXHoras(momentoAtual, tempoRestanteDaQtdDiasDoPrazo);
			verificarHorarioUtil(momentoAtual);
			verificarFimDeSemana(momentoAtual);
			prazoFinal = new Timestamp(momentoAtual.getTimeInMillis());
		}

		return prazoFinal;
	}

	public void verificarHorarioUtil(Calendar momentoAtual) {
		int horaAtual = momentoAtual.get(Calendar.HOUR_OF_DAY);

		while (horaAtual >= 17 || horaAtual < 8) {
			adicionaXHoras(momentoAtual, 15);
			horaAtual = momentoAtual.get(Calendar.HOUR_OF_DAY);
		}

	}

	public void verificarFimDeSemana(Calendar momentoAtual) {
		int diaDaSemana = momentoAtual.get(Calendar.DAY_OF_WEEK);

		if (diaDaSemana == Calendar.SATURDAY) {
			adicionaXHoras(momentoAtual, 48);
		} else if (diaDaSemana == Calendar.SUNDAY) {
			adicionaXHoras(momentoAtual, 24);
		}

	}

	public long adicionaXHoras(Calendar momentoAtual, int horaX) {
		Double correcaoDeTempo = new Double(1000 * horaX * 60 * 60);
		momentoAtual.setTimeInMillis(momentoAtual.getTimeInMillis() + correcaoDeTempo.longValue());
		return momentoAtual.getTimeInMillis();
	}

}
