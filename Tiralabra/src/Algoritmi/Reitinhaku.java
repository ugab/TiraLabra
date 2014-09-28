/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Algoritmi;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * @author Jaakko
 * 
 * Luokka reitinhaku etsii lyhyimmän reitin verkosta lähtö ja maali pisteiden välillä.
 * 
 */

public class Reitinhaku {
  
    public Verkko verkko;
    
    public PriorityQueue<Solmu> AvoinLista;
    
    
/**
 * Konstruktori luo verkon sekä AvoinListan jossa on solmut joihin voidaan mennä. 
 * Konstruktori lisää tähän prioriteettijonoon aloituspisteen ja laittaa sen 
 * edeltäväksi solmuksi itsensä.
 * 
 * @param syöte verkko jossa reitti etsitään.
 * @param Lähtö reitin lähtöpiste.
 * 
 */          
    
    public Reitinhaku(Verkko syöte, Point Lähtö){
        verkko=syöte;

        Comparator<Solmu> comparator = new LukuVertaaja();
        AvoinLista=new PriorityQueue<Solmu>(12, comparator);
        
        
        Solmu lähtösolmu=verkko.taulukko[Lähtö.x][Lähtö.y];
        
        AvoinLista.add(lähtösolmu);
        lähtösolmu.Edeltävä=lähtösolmu;
        
        

    }
    
/**
 * Haku metodin looppi ottaa AvoinListasta solmun jolla on pienin heurestiikka-arvo, 
 * ja antaa sen parametrinä TarkistaViereiset metodille. Looppi loppuu kun maalipiste on
 * käsittelyssä. Metodi myös maalaa kaikki käydyt pisteet.
 * 
 */        
    
    public void Haku(){

        
        Solmu käsittelyssä=new Solmu(6, 6, 6, 6);
        
        while(käsittelyssä.Heurestiikaarvo!=0){
            
            käsittelyssä=AvoinLista.poll();
            
            verkko.kuva.setRGB(käsittelyssä.koordinaattiX, käsittelyssä.koordinaattiY, new Color(100,100,100).getRGB());
            
            TarkistaViereiset(käsittelyssä);
            
        }
        
        PiirräReitti(käsittelyssä);
         
    }
    
/**
 * Tarkistaa jokaisen viereisen solmun sen varalta että se on seinä, jos ei ole 
 * niin kutsuu metodia Lisää sille solmulle.
 * 
 * 
 *  @param Käsittelyssä Käsittelyssä oleva solmu
 */   
    
    public void TarkistaViereiset(Solmu Käsittelyssä){
                
        
        if(verkko.taulukko[Käsittelyssä.koordinaattiY][Käsittelyssä.koordinaattiX+1].haeSeina()==false){
            Lisää(Käsittelyssä.koordinaattiX+1, Käsittelyssä.koordinaattiY, Käsittelyssä);

        }
        if(verkko.taulukko[Käsittelyssä.koordinaattiY+1][Käsittelyssä.koordinaattiX].haeSeina()==false){
            Lisää(Käsittelyssä.koordinaattiX, Käsittelyssä.koordinaattiY+1, Käsittelyssä);

        }
        if(verkko.taulukko[Käsittelyssä.koordinaattiY][Käsittelyssä.koordinaattiX-1].haeSeina()==false){
            Lisää(Käsittelyssä.koordinaattiX-1, Käsittelyssä.koordinaattiY, Käsittelyssä);

        }
        if(verkko.taulukko[Käsittelyssä.koordinaattiY-1][Käsittelyssä.koordinaattiX].haeSeina()==false){
            Lisää(Käsittelyssä.koordinaattiX, Käsittelyssä.koordinaattiY-1, Käsittelyssä);

        }
        
        
    }
    
/**
 * Jos solmu ei ole ollet avoimessa listassa aikaisemmin niin metodi laittaa 
 * sen sinne ja samalla asettaa sen Edeltavä solmuksi solmun "Käsittelyssä".
 * 
 * 
 * @param x Koordinaatti x
 * @param y Koordinaatti y
 * @param Käsittelyssä Käsittelyssä oleva solmu
 */   
    public void Lisää(int x, int y, Solmu Käsittelyssä){
        
        if(verkko.taulukko[x][y].Edeltävä==null){
            verkko.taulukko[x][y].Edeltävä=Käsittelyssä;
            verkko.taulukko[x][y].Reittipituus=Käsittelyssä.Reittipituus+1;
            AvoinLista.add(verkko.taulukko[x][y]);
        }
        
    }
    
/**
 * Looppi maalaa aina käsittelyssä olevan solmun/pisteen ja siirtyy sitten 
 * kyseisen solmun edeltävä solmuun. Lähtöpisteeseen saavuttaessa looppi loppuu.
 * 
 * 
 * @param syöte Maalipiste josta reitin piirtäminen alkaa.
 */     
    
    
    
    public void PiirräReitti(Solmu syöte){
        
        Solmu käsittelyssä=syöte;
        
        while(true){
            
            verkko.kuva.setRGB(käsittelyssä.koordinaattiX, käsittelyssä.koordinaattiY, 255);
            
            if(käsittelyssä==käsittelyssä.Edeltävä){
                break;
            }else{
                käsittelyssä=käsittelyssä.Edeltävä;
            }
            
        }
        
        
    }
    
    
}