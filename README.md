# Uued postitused mailile
Eesm�rk oli luua rakendus, mis k�iks m��ratud intervalliga veebilehel, otsiks sealt uued postitused ja
saadaks need e-mailiga soovitud aadressile.

## N�uded
* Veebiserver java v�imalusega
* Mailiserver (nt. Postfix)
* data kaust koos k�ikide *site'ides* mainitud failidega ja kirjutamis�igused neile

## TODO, m�tted
* Turvaline faili kirjutamine
* �kki saaks mitte iga kord faili lugeda vaid hoida vanu linke m�lus ja iga kord terve fail uuesti kirjutada?
* Mitu konfiguratsioonisektorit ja mitu Threadi erinevate lehtede jaoks
* Andmefaili loomine, kui seda pole veel olemas
* CONFIG paremini implementeerida (mitte klassidesse edasi passida, vaid global)
* Testid
* Host protsess paremini l�bi m�elda
* Lingi l�pu (mis iganes osa) l�ikamine �ldistada