/*
MIT License

Copyright (c) 2025-2026, Nuno Datia, Matilde Pato, ISEL

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package isel.sisinf.ui;

import isel.sisinf.jpa.entity.Cliente;
import isel.sisinf.jpa.entity.Posicao;
import isel.sisinf.jpa.entity.ValorPosicao;
import isel.sisinf.jpa.service.PortefolioService;
import isel.sisinf.jpa.service.PosicaoService;
import isel.sisinf.jpa.service.ClienteService;
import isel.sisinf.jpa.service.InstrumentoService;
import isel.sisinf.jpa.Dal;

import jakarta.persistence.EntityManager; 

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Optional;

/**
 * 
 * Didactic material to support 
 * to the curricular unit of 
 * Introduction to Information Systems 
 *
 * The examples may not be complete and/or totally correct.
 * They are made available for teaching and learning purposes and 
 * any inaccuracies are the subject of debate.
 */

interface DbWorker
{
    void doWork();
}
class UI implements AutoCloseable
{
    private enum Option
    {
        // DO NOT CHANGE ANYTHING!
        Unknown,
        Exit,
        createClient,
        createPortfolio,
        listPositions,
        updateInvestments,
        updateClient,
        about
    }
    private static UI __instance = null;
    private static Scanner __s = null;
  
    private HashMap<Option,DbWorker> __dbMethods;

    private UI()
    {
        // DO NOT CHANGE ANYTHING!
        __dbMethods = new HashMap<Option,DbWorker>();
        __dbMethods.put(Option.createClient, () -> UI.this.createClient());
        __dbMethods.put(Option.createPortfolio, () -> UI.this.createPortfolio()); 
        __dbMethods.put(Option.listPositions, () -> UI.this.listPositions());
        __dbMethods.put(Option.updateInvestments, () -> UI.this.updateInvestments());
        __dbMethods.put(Option.updateClient, () ->  UI.this.updateClient());
        __dbMethods.put(Option.about, new DbWorker() {public void doWork() {UI.this.about();}});
    }

    public static UI getInstance()
    {
        // DO NOT CHANGE ANYTHING!
        if(__instance == null)
        {
            __instance = new UI();
        }
        return __instance;
    }

    public static Scanner getScanner()
    {
        if(__s == null)
        {
            __s = new Scanner(System.in);
        }
        return __s;
    }

    private Option DisplayMenu()
    {
        Option option = Option.Unknown;
        Scanner s = getScanner();
        try
        {
            // DO NOT CHANGE ANYTHING!
            System.out.println("  ___ ___                 ");
            System.out.println(" | __| _ \\__ _ _  _ ___  ");
            System.out.println(" | _||  _/ _` | || (_-<  ");
            System.out.println(" |___|_| \\__,_|\\_,_/__/  ");
            System.out.println("        Management DEMO   ");
            System.out.println();
            System.out.println("1. Exit");
            System.out.println("2. Create Client");
            System.out.println("3. Create Portfolio");
            System.out.println("4. List Positions");
            System.out.println("5. Update Investments");
            System.out.println("6. Update Client");
            System.out.println("7. About");
            System.out.print(">");
            int result = s.nextInt();
            option = Option.values()[result];
        }
        catch(RuntimeException ex)
        {
            //nothing to do.
        }
        
        return option;

    }
    private static void clearConsole() throws Exception
    {
        // DO NOT CHANGE ANYTHING!
        for (int y = 0; y < 25; y++) //console is 80 columns and 25 lines
            System.out.println("\n");
    }

    public void Run() throws Exception
    {
        // DO NOT CHANGE ANYTHING!
        Option userInput;
        do
        {
            clearConsole();
            userInput = DisplayMenu();
            clearConsole();
            try
            {
                __dbMethods.get(userInput).doWork();
                System.in.read();
            }
            catch(NullPointerException ex)
            {
                //Nothing to do. The option was not a valid one. Read another.
            }

        }while(userInput!=Option.Exit);
    }

    /**
    To implement from this point forward. 
    -------------------------------------------------------------------------------------     
        IMPORTANT:
    --- DO NOT MESS WITH THE CODE ABOVE. YOU JUST HAVE TO IMPLEMENT THE METHODS BELOW ---
    --- Other Methods and properties can be added to support implementation. 
    ---- Do that also below                                                         -----
    -------------------------------------------------------------------------------------
    
    */


    //Implement an AutoClosable object. 
    // If needed you can add more stuff to clean at the end
    @Override
    public void close()
    {
        if(__s != null)
        {
            __s.close();
            __s = null;
        }
    }

    public ClienteService clienteService = new ClienteService();
    public PortefolioService portefolioService = new PortefolioService();
    public PosicaoService posicaoService = new PosicaoService();
    public InstrumentoService instrumentoService = new InstrumentoService();

    private void createClient() {
        Scanner scanner = UI.getScanner();
        scanner.nextLine();
        System.out.println("Enter Client Name:");
        String name = scanner.nextLine();

        if(name.isBlank()){
            System.out.println("Invalid name: it must have at least one character.");
            System.out.println("Press enter to return");
            return;
        }

        System.out.println("Enter Client NIF:");
        String nif = scanner.nextLine();

        if(!Utils.isValidTaxNumber(nif)){
            System.out.println("Invalid NIF. Please use 9 digits.");
            System.out.println("Press enter to return");
            return;
        }

        System.out.println("Enter Client Citizen Card:");
        String cartCidadao = scanner.nextLine();

        if(!Utils.isValidCartCidadao(cartCidadao)){
            System.out.println("Invalid CC. Please use 8 digits + 2 letters + 1 digit.");
            System.out.println("Press enter to return");
            return;
        }

        System.out.println("Select contact type: 'email', 'telefone' .");
        String contactType = scanner.nextLine();
        String contact;

        if(contactType.equals("email")){

            System.out.println("Enter Client Email:");
            contact = scanner.nextLine();

            //existe uma verificação da validade do endereço de email na BD, mas é mais rápido mostrar um erro ao utilizador aqui
            //  e pedir para inserir novamente do que fazer o processo todo que falha na BD

            if(!Utils.isValidEmail(contact)){
                System.out.println("Invalid email format. Use : <string>@<string>.<string>");
                System.out.println("Press enter to return");
                return;
            }

        }else if(contactType.equals("telefone")){
            System.out.println("Enter Client Telephone:");
            contact = scanner.nextLine();

            if(!Utils.isValidTelephone(contact)){
                System.out.println("Invalid telephone number");
                System.out.println("Press enter to return");
                return;
            }

        }else{
            System.out.println("Invalid option. Please enter 'email' or 'telefone'.");
            System.out.println("Press enter to return");
            return;
        }

        System.out.println("Enter a contact description:");
        String descricao = scanner.nextLine();

        
        boolean exists = clienteService.existsByTaxNumberOrCC(cartCidadao, nif);
        if(exists) {
            System.out.println("Client with the same CC or NIF already exists.");
            System.out.println("Press enter to return");
            return;
        }

        try{
            clienteService.criarCliente(name, nif, cartCidadao, contactType, contact, descricao);
            System.out.println("Client created successfully!");
        }catch( Exception e){

            System.out.println("Error creating client: " + e.getMessage());
        }
    }
  
    private void createPortfolio()
    {
        Scanner scanner = UI.getScanner();
        scanner.nextLine();

        
        System.out.println("Enter Client NIF:");
        String nif = scanner.nextLine();

        Optional<Cliente> clienteOpt = clienteService.findByNif(nif);

        if(clienteOpt.isEmpty()) {
            System.out.println("Client NIF not found.");
            System.out.println("Press enter to return");
            return;
        }

        System.out.println("Enter Portfolio Name:");
        String name = scanner.nextLine();

        if(name.isBlank()){
            System.out.println("Invalid name: it must have at least one character.");
            System.out.println("Press enter to return");
            return;
        }

        try{
            portefolioService.criarPortefolio(name, clienteOpt.get());
            System.out.println("Portfolio created successfully!");
        }catch( Exception e){

            System.out.println("Error creating portfolio: " + e.getMessage());
        }

    }

    private void listPositions()
    {
        Scanner scanner = UI.getScanner();
        scanner.nextLine();

        System.out.println("Enter Client NIF:");
        String nif = scanner.nextLine();

        if(!Utils.isValidTaxNumber(nif)){
            System.out.println("Invalid NIF. Please use 9 digits.");
            System.out.println("Press enter to return");
            return;
        }
        
        List<ValorPosicao> posicoes = posicaoService.listByClientNif(nif);

        if(posicoes.isEmpty()){
            System.out.println("Client doesn't have any positions.");
        }else{
            BigDecimal totalPortfolio = BigDecimal.ZERO;

            for (ValorPosicao vp : posicoes) {
                System.out.println(
                    vp.getId().getInstrumentoIsin() + " | " +
                    vp.getQuantidade() + " | " +
                    vp.getValorActual() + " | " +
                    vp.getValorTotal()
                );

                totalPortfolio = totalPortfolio.add(vp.getValorTotal());
            }
            System.out.println("--------------------------------");
            System.out.println("Portfolio total: " + totalPortfolio);
        }
        
    }

    private void updateInvestments() {
        Scanner scanner = UI.getScanner();
        scanner.nextLine();
        //Actualizar o valor diário de um instrumento, recorrendo ao procedimento criado na alínea 4;
        
        //obter o isin do instrumento, valor, e data
        System.out.println("Insert Instrument ISIN:");
        String isin = scanner.nextLine();

        if(!Utils.isValidIsin(isin)){
            System.out.println("Invalid ISIN. Please use ");
            System.out.println("Press enter to return.");
            return;
        }

        System.out.println("Insert Intrument value:");
        String value = scanner.nextLine();
        if(!Utils.isNumeric(value)){
            System.out.println("Please insert a number. Decimal separator is '.' instead of ',' .");
            System.out.println("Press enter to return.");
            return;
        }

        BigDecimal currentValue = new BigDecimal(value);

        LocalDate timeNow = LocalDate.now();

        System.out.println("Isin: " + isin + ", Value: " + currentValue + ", time: " + timeNow);

        try{
            instrumentoService.actualizarValorDiario(isin, timeNow, currentValue);
            System.out.println("Updated Instrument value!");
        }catch(Exception e){
            System.out.println("Error ocurred while updating investments : " + e.getMessage());
        }
        
    }

    private void updateClient()
    {
        Scanner scanner = UI.getScanner();
        scanner.nextLine();

        //Actualização dos dados de um cliente usando o mecanismo optimistic locking.

        System.out.println("Enter Client Name:");
        String name = scanner.nextLine();

        if(name.isBlank()){
            System.out.println("Invalid name: it must have at least one character.");
            System.out.println("Press enter to return");
            return;
        }

        System.out.println("Enter Client NIF:");
        String nif = scanner.nextLine();

        if(!Utils.isValidTaxNumber(nif)){
            System.out.println("Invalid NIF. Please use 9 digits.");
            System.out.println("Press enter to return");
            return;
        }

        System.out.println("Old contact:");
        System.out.println("Select contact type: 'email', 'telefone' .");
        String contactType = scanner.nextLine();
        String oldContact;

        if(contactType.equals("email")){

            System.out.println("Enter Client Email:");
            oldContact = scanner.nextLine();

            //existe uma verificação da validade do endereço de email na BD, mas é mais rápido mostrar um erro ao utilizador aqui
            //  e pedir para inserir novamente do que fazer o processo todo que falha na BD

            if(!Utils.isValidEmail(oldContact)){
                System.out.println("Invalid email format. Use : <string>@<string>.<string>");
                System.out.println("Press enter to return");
                return;
            }

        }else if(contactType.equals("telefone")){
            System.out.println("Enter Client Telephone:");
            oldContact = scanner.nextLine();

            if(!Utils.isValidTelephone(oldContact)){
                System.out.println("Invalid telephone number");
                System.out.println("Press enter to return");
                return;
            }

        }else{
            System.out.println("Invalid option. Please enter 'email' or 'telefone'.");
            System.out.println("Press enter to return");
            return;
        }

        System.out.println("New contact:");
        
        String newContact;

        if(contactType.equals("email")){

            System.out.println("Enter Client Email:");
            newContact = scanner.nextLine();

            //existe uma verificação da validade do endereço de email na BD, mas é mais rápido mostrar um erro ao utilizador aqui
            //  e pedir para inserir novamente do que fazer o processo todo que falha na BD

            if(!Utils.isValidEmail(newContact)){
                System.out.println("Invalid email format. Use : <string>@<string>.<string>");
                System.out.println("Press enter to return");
                return;
            }

        }else if(contactType.equals("telefone")){
            System.out.println("Enter Client Telephone:");
            newContact = scanner.nextLine();

            if(!Utils.isValidTelephone(newContact)){
                System.out.println("Invalid telephone number");
                System.out.println("Press enter to return");
                return;
            }

        }else{
            System.out.println("Invalid option. Please enter 'email' or 'telefone'.");
            System.out.println("Press enter to return");
            return;
        }

        System.out.println("Enter a new contact description:");
        String descricao = scanner.nextLine();

        
        boolean exists = !clienteService.findByNif(nif).isEmpty();
        if(!exists) {
            System.out.println("No Client with this CC or NIF exists.");
            System.out.println("Press enter to return");
            return;
        }

        try{
            clienteService.atualizarCliente(nif, name, contactType, oldContact, newContact, descricao);
            System.out.println("Client updated successfully!");
        }catch( Exception e){

            System.out.println("Error creating client: " + e.getMessage());
        }

        // obter dados a atualizar V
        //precisamos de alterar a entity do Cliente para adicionar a anotação @Version V
        // na query que usamos, temos de fazer setLockMode para OPTIMISTIC
        
    }

    private void about()
    {
        System.out.println("Brought to you by a wonderful set of professors!");
        System.out.println("+ David Seves 50495");
        System.out.println("+ Dinis Melo 51500");
        System.out.println("+ Gabriel Subutzki 50142");
        System.out.println("DAL version:"+ isel.sisinf.jpa.Dal.version());
        System.out.println("Core version:"+ isel.sisinf.model.Core.version());
        
    }

}

public class App{
    public static void main(String[] args) throws Exception{
       try(UI ui = UI.getInstance())
        {
            ui.Run();
        }
    }
}
