/*
 #tipos de movimentos
 vou ter a posicao de "origem" e "destino" para criar o script

 #acao Movimento
 #pega Origem
 #devolve Destino

 #acao Captura
 #pega Origem    ->Atacante
 #devolve Temp
 #pega Destino   ->PecaMorta
 #devolve Lixao
 #pega Temp
 #devolve Destino
 */
package tcc.Robix;

/**
 *
 * @author Anderson
 */
public class MakeScript {

    /*
     Tipos de Movimentos
     */
    private String[] MovSimples
            = {"pegaIda; origem; pegaVolta;\n",
                "devolveIda; destino; devolveVolta;\n"};

    private String MovCaptura
            = "devolveIda; temp; devolveVolta;\n"
            + "pegaIda; destino; pegaVolta;\n"
            + "devolveIda; lixao; devolveVolta;\n"
            + "pegaIda; temp; pegaVolta;\n";

    /*
     Macros Fixos para os Movimentos
     */
    private String macroBasico
            = "macro repouso; move 1 to 1400, 2 to 350, 3 to -930, 4 to -650, 5 to -800; end\n"
            + "macro up_init; move 5 to -800; move 1 to 0, 2 to 0, 3 to 0, 4 to 0, 6 to 0; end\n"
            + "macro centro_direita; move 1 to -840, 2 to 880, 3 to 850, 4 to 770; end\n"
            + "macro centro_esquerda; move 1 to 840, 2 to -880, 3 to -850, 4 to -770; end\n"
            + "macro pegaIda; up_init; move 7 to -1400; end\n"
            + "macro pegaVolta; move 7 to 20; up_init; end\n"
            + "macro devolveIda; up_init; end\n"
            + "macro devolveVolta; move 7 to -1400; up_init; repouso; end\n";

    private String macroCaptura
            = "macro lixao; "
            + "move 1 to 830, 2 to 120, 3 to -50, 4 to -220; move 5 to -200, 6 to -1280; end\n"
            + "macro temp; "
            + "move 1 to 1500, 2 to 790, 3 to -730, 4 to -1500; move 5 to 670, 6 to 0; end\n";

    private String macroDestinos = "";

    private boolean isCaptura;

    public MakeScript(String origem, String destino, boolean isCaptura) {
        this.isCaptura = isCaptura;
        makeMacroDestinos(origem, destino);
    }

    private void makeMacroDestinos(String origem, String destino) {
        macroDestinos
                = "macro origem; " + origem + " end\n"
                + "macro destino; " + destino + " end\n";
    }

    public String getScript() {

        String script = macroBasico + macroDestinos;

        if (isCaptura) {
            script += macroCaptura
                    + MovSimples[0]
                    + MovCaptura
                    + MovSimples[1];
        } else {
            script += MovSimples[0]
                    + MovSimples[1];
        }

        return script;
    }

//    public static void main(String[] args) {
//        MakeScript teste = new MakeScript("A", "B", true);
//
//        System.out.println(teste.getScript() + ".\n");
//
//        teste = new MakeScript("C", "D", false);
//
//        System.out.println(teste.getScript() + ".\n");
//    }

}
