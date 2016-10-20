# Uued postitused mailile
Eesmärk oli luua rakendus, mis käiks määratud intervalliga veebilehel, otsiks sealt uued postitused ja
saadaks need e-mailiga soovitud aadressile.

## Nõuded
* Veebiserver java võimalusega
* Mailiserver (nt. Postfix)
* data kaust koos kõikide *site'ides* mainitud failidega ja kirjutamisõigused neile

## TODO, mõtted
* Turvaline faili kirjutamine
* Äkki saaks mitte iga kord faili lugeda vaid hoida vanu linke mälus ja iga kord terve fail uuesti kirjutada?
* Mitu konfiguratsioonisektorit ja mitu Threadi erinevate lehtede jaoks
* Andmefaili loomine, kui seda pole veel olemas
* CONFIG paremini implementeerida (mitte klassidesse edasi passida, vaid global)
* Testid
* Host protsess paremini läbi mõelda
* Lingi lõpu (mis iganes osa) lõikamine üldistada