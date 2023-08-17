# Todo

# Pretvori veliko service metod v Use cases
# Componente zdruzi po obiki (dialogs, tables, etc...)
# Vsaka komponenta naj ima samo en model kateri skrbi za representacijo!
# Use case uporablja vec komponent in specijalne modele za izvedbo neke zelo kompleksne akcije... (sestavi test, ) Componente ki sestavljajo use case ne smejo biti uporabljene v routih, v routih je lahko uporabljen samo glavni use case!!!
# Routi so namenjeni samo prikazovalni logiki in prikazujejo surove podatke component... Routi so brez modelov! Routov component skrbi samo za
# uspesno komunikacijo component ki jih prikazuje na strani! BREZ MODELOV!!!!

# Arhitektura mora biti v drevesni strukturi (na vrhu naj bo route) vse ostalo naj sluzi routu! V routu naj ne bo veliko logike, route naj bo kot usecase v serverju.
