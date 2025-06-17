package ftn.bp2;

import ftn.bp2.ui.VinarijaUIHandler;

public class Main {
    public static void main(String[] args) {

        System.out.println("Pokretanje sistema vinarije...");

        try {
            VinarijaUIHandler uiHandler = new VinarijaUIHandler();
            uiHandler.start();
        } catch (Exception e) {
            System.err.println("Greška pri pokretanju aplikacije: " + e.getMessage());
            e.printStackTrace();
        }
    }
}