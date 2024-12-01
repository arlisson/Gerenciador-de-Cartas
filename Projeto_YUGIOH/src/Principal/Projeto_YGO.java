/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Principal;

import DAO.CartaDAO;
import Model.Carta;
import View.Tela;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 *
 * @author T-GAMER
 */
public class Projeto_YGO {

    public static void main(String[] args){
    Tela tela = new Tela();
    tela.setVisible(true);

    //System.out.print("jdbc:sqlite:"+Projeto_YGO.class.getResource("/Resources/yugioh.db").toString().replace("file:/",""));
       
     Path filePath = Path.of("yugioh.db");

        // Verifica se o arquivo existe
        if (Files.exists(filePath)) {
            System.out.println("O arquivo existe.");
        } else {
            System.out.println("O arquivo não existe.");
        }
    
       
     

    }
}
