package br.ufrn.simba.instancia.estrategias;

import br.ufrn.simba.dispositivo.seguranca.Bollands;
import br.ufrn.simba.dispositivo.seguranca.Sirene;
import br.ufrn.simba.instancia.Instancia;
import br.ufrn.simba.model.Estado;
import br.ufrn.simba.seguranca.NotificacaoEmail;
import br.ufrn.simba.seguranca.NotificacaoPolicia;
import br.ufrn.simba.seguranca.NotificacaoSlack;
import org.apache.commons.mail.EmailException;

import java.io.IOException;
import java.util.List;

/**
 * Created by joao on 10/06/17.
 */
public class EstrategiaSegurancaFechadoCamera extends EstrategiaSegurancaFechado {

    public EstrategiaSegurancaFechadoCamera() {
    	// 
        this.addAlerta(new NotificacaoEmail());
        // 
        // 
        this.addAlerta(new Sirene(false, 5));
        // 
        // 
        this.addAlerta(new NotificacaoPolicia());
        // 
        // 
        this.addAlerta(new NotificacaoSlack());
        // 
    }

    @Override
    public void execute(List<Estado> estados) throws IOException, EmailException {
        boolean notificar = false;
        for (final Estado estado : estados) {
        	// 
            if (estado.getHash() == Instancia.sensorMovimentoHash) {
                notificar = estado.getValor() < 600;
            }
            // 

            if (notificar && estado.getHash() == Instancia.cameraMovimentoHash) {
                notificar = estado.getValor() == 1;
            }
        }

        if (notificar) {
            notificar(estados);
        }
    }
}
