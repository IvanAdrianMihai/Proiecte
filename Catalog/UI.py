'''
Created on Nov 9, 2019

@author: Ivan Adrian
'''
from Controller import ServiceStudent, ServiceDisciplina, ServiceNota,\
    ServiceNoteDisciplineDTO,ServiceMedieDisciplineDTO,ServiceMedieNoteAcordateDTO
from Repository import RepositoryNoteDisciplineDTO,RepositoryMedieDisciplineDTO,RepositoryMedieNoteAcordateDTO
from Domain import ValidatorException, RepositoryException
class Ui:
    '''
    Tip abstract de date pentru meniu
    Domain: {repoS, repoD, repoN sunt clase care retin studentii, disciplinele si asignarea de note
            service este clasa care prelucreaza datele si gestioneaza adaugarile in 'baza de date'}
    '''
    def __init__(self,repoS,repoD,repoN,repoNoteDTO,repoMedieDTO,repoAcordateDTO):
        '''
        Crearea unei noi instante Ui
        '''
        self.__serviceS = ServiceStudent(repoS)
        self.__serviceD = ServiceDisciplina(repoD)
        self.__serviceN = ServiceNota(repoN,repoS,repoD,self.__serviceS,self.__serviceD)
        self.__serviceNoteDTO = ServiceNoteDisciplineDTO(repoS,repoN,repoD,repoNoteDTO)
        self.__serviceMedieDTO = ServiceMedieDisciplineDTO(repoS,repoN,repoD,repoMedieDTO)
        self.__serviceAcordateDTO = ServiceMedieNoteAcordateDTO(repoS,repoN,repoD,repoAcordateDTO)
        self.__repoS = repoS
        self.__repoD = repoD
        self.__repoN = repoN
        self.__repoNoteDTO =repoNoteDTO
        self.__repoMedieDTO=repoMedieDTO
        self.__repoAcordateDTO=repoAcordateDTO
    def meniu(self):
        '''
        Metoda printeaza meniul si realizeaza interactiunea cu utilizatorul
        '''
        print('Alegeti din urmatoarea lista:')
        print('    1.  Adaugati student')
        print('    2.  Adaugati disciplina')
        print('    3.  Sterge student')
        print('    4.  Sterge disciplina')
        print('    5.  Modifica student')
        print('    6.  Modifica disciplina')
        print('    7.  Cauta student')
        print('    8.  Cauta disciplina')
        print('    9.  Asignare nota')
        print('    10. Sterge nota')
        print('    11. Adaugati studenti aleatorii')
        print('    12. Adaugati discipline aleatorii')
        print('    13. Afisare lista studentilor si a notelor acestora la o disciplina data, ordonati dupa note si nume')
        print('    14. Afisati primii 20% dintre studenti ordonati dupa media notelor la toate disciplinele')
        print('    15. Afisati lista profesorilor ordonati crescator dupa media notelor acordate de acestia')
        print('    16. Iesire')
        while True:
            #Se valideaza ind
            while True:
                try:
                    ind = int(input('Introduceti indicativul: '))
                    break
                except ValueError:
                    print('Indicativul apartine intervalului [1,16]: ')
            if ind == 1:
                #Se valideaza datele Studentului
                while True:
                    try:
                        idStudent = input('Introduceti id-ul studentului: ')
                        nume = input('Introduceti numele studentului: ')
                        self.__serviceS.adaugaStudent(idStudent,nume)
                        break
                    except ValidatorException as mesaj:
                        print(mesaj)
                    except RepositoryException as mesaj:
                        print(mesaj)
                #Daca datele sunt corecte se afisaza lista studentilor
                print(self.__serviceS.afisazaStudenti())             
            elif ind == 2:
                #Se valideaza datele Disciplinei
                while True:
                    try:
                        idDisciplina = input('Introduceti id-ul disciplinei: ')
                        nume = input('Introduceti numele disciplinei: ')
                        profesor = input ('Introduceti numele profesorului: ')
                        self.__serviceD.adaugaDisciplina(idDisciplina,nume,profesor)
                        break
                    except ValidatorException as mesaj:
                        print(mesaj)
                    except RepositoryException as mesaj:
                        print(mesaj)
                #Daca datele sunt corecte se afisaza lista disciplinelor
                print(self.__serviceD.afisazaDiscipline())
            elif ind == 3:
                #Se vlideaza id Studentului care va fi sters
                if len(self.__repoS.getListaStudenti()) == 0:
                    print('    Nu se poate sterge deoarece nu exista niciun student')
                else:
                    while True:
                        try:
                            idStudent = input('Introduceti id-ul studentului: ')
                            self.__serviceS.stergeStudent(idStudent)
                            break
                        except ValidatorException as mesaj:
                            print(mesaj)
                        except RepositoryException as mesaj:
                            print(mesaj)
                    #Daca datele sunt corecte se afisaza lista studentilor
                    print(self.__serviceS.afisazaStudenti())
            elif ind == 4:
                #Se valideaza id-ul disciplinei care se va sterge
                if len(self.__repoD.getListaDiscipline()) == 0:
                    print('    Nu se poate sterge deoarece nu exista nicio disciplina')
                else:
                    while True:
                        try:
                            idDisciplina = input('Introduceti id-ul discipline: ')
                            self.__serviceD.stergeDisciplina(idDisciplina)
                            break
                        except ValidatorException as mesaj:
                            print(mesaj)
                        except RepositoryException as mesaj:
                            print(mesaj)
                    #Daca datele sunt corecte se afisaza lista disciplinelor
                    print(self.__serviceD.afisazaDiscipline())
            elif ind == 5:
                if len(self.__repoS.getListaStudenti()) == 0:
                    print('    Nu se poate modifica deoarece nu exista niciun student')
                else:
                    #Se vlideaza id Studentului care va fi modificat
                    while True:
                        try:
                            idActual = input('Introduceti id-ul studentului: ')
                            self.__serviceS.getValS().validareIdStudent(idActual)    
                            self.__serviceS.getValRepS().existaIdStudent(idActual)    
                            break
                        except RepositoryException as mesaj:
                            print(mesaj)
                        except ValidatorException as mesaj:
                            print(mesaj)
                    #Se valideaza datele de modificare a studentului
                    while True:
                        try:                   
                            idNou = input('Introduceti noul id al studentului: ')
                            numeNou = input('Introduceti noul nume al studentului: ')
                            self.__serviceS.modificaStudent(idActual,idNou,numeNou)
                            break
                        except RepositoryException as mesaj:
                            print(mesaj)
                        except ValidatorException as mesaj:
                            print(mesaj)
                    #Daca datele sunt corecte se afisaza lista studentilor
                    print(self.__serviceS.afisazaStudenti())
            elif ind == 6:
                if len(self.__repoD.getListaDiscipline()) == 0:
                    print('    Nu se poate modifica deoarece nu exista nicio disciplina')
                else:
                    #Se vlideaza id Disciplinei care va fi modificata
                    while True:
                        try:
                            idActual = input('Introduceti id-ul discipline: ') 
                            self.__serviceD.getValD().validareIdDisciplina(idActual)
                            self.__serviceD.getValRepD().existaIdDisciplina(idActual)    
                            break
                        except RepositoryException as mesaj:
                            print(mesaj)
                        except ValidatorException as mesaj:
                            print(mesaj)
                    #Se valideaza datele de modificare a disciplinei
                    while True:
                        try:                                   
                            idNou = input('Introduceti noul id al disciplinei: ')
                            numeNou = input('Introduceti noul nume al disciplinei: ')
                            profesorNou = input ('Introduceti noul nume al profesorului: ')
                            self.__serviceD.modificaDisciplina(idActual,idNou,numeNou,profesorNou)
                            break
                        except RepositoryException as mesaj:
                            print(mesaj)
                        except ValidatorException as mesaj:
                            print(mesaj)
                    #Daca datele sunt corecte se va modifica datele Disciplinei
                    print(self.__serviceD.afisazaDiscipline())
            elif ind == 7:
                if len(self.__repoS.getListaStudenti()) == 0:
                    print('    Nu exista niciun student adaugat')
                else:
                    #Se valiteaza si afisaza studentul cu id-ul introdus
                    while True:
                        try:
                            idStudent = input('Introduceti id-ul studentului cautat: ') 
                            student = self.__serviceS.cautaStudent(idStudent)    
                            break
                        except RepositoryException as mesaj:
                            print(mesaj)
                        except ValidatorException as mesaj:
                            print(mesaj)
                    afis = 'Studentul ' + student.getNume() + ' cu id-ul ' + student.getIDStudent()
                    print(afis)
            elif ind == 8:
                if len(self.__repoD.getListaDiscipline()) == 0:
                    print('    Nu exista nicio disciplina adaugata')
                else:
                    #Se valiteaza si afisaza disciplina cu id-ul introdus
                    while True:
                        try:
                            idDisciplina = input('Introduceti id-ul disciplinei cautatate: ') 
                            disciplina = self.__serviceD.cautaDisciplina(idDisciplina)   
                            break
                        except RepositoryException as mesaj:
                            print(mesaj)
                        except ValidatorException as mesaj:
                            print(mesaj)
                    afis = 'Disciplina ' + disciplina.getNume() + ' predata de ' + disciplina.getProfesor() + ' cu id-ul ' + disciplina.getIDdisciplina()
                    print(afis) 
            elif ind == 9:
                mesaj = ''
                if len(self.__repoS.getListaStudenti()) == 0:
                    mesaj = '    Nu exista niciun student adaugat'
                if len(self.__repoD.getListaDiscipline()) == 0:
                    if mesaj != '':
                        mesaj = mesaj + '\n'
                    mesaj = mesaj + '    Nu exista nicio disciplina adaugata'
                if mesaj != '':
                    print(mesaj)
                else:
                    #Se valideaza id-ul disciplinei si al studentului caruia i se va asigna nota
                    while True:
                        try:
                            idStudent = input('Introduceti id-ul studentului: ') 
                            idDisciplina = input('Introduceti id-ul disciplinei: ') 
                            nota = input('Introduceti nota: ')
                            self.__serviceN.asignareNota(idStudent, idDisciplina, nota)
                            break
                        except RepositoryException as mesaj:
                            print(mesaj)
                        except ValidatorException as mesaj:
                            print(mesaj)
                    #Daca datele sunt corecte se va adauga nota
                    print(self.__serviceN.afisazaNote())
            elif ind == 10:
                mesaj = ''
                if len(self.__repoS.getListaStudenti()) == 0:
                    mesaj = '    Nu exista niciun student adaugat'
                if len(self.__repoD.getListaDiscipline()) == 0:
                    if mesaj != '':
                        mesaj = mesaj + '\n'
                    mesaj = mesaj + '    Nu exista nicio disciplina adaugata'
                if len(self.__repoN.getListaNote()) == 0:
                    if mesaj != '':
                        mesaj = mesaj + '\n'
                    mesaj = mesaj +'    Nu exista note acordate niciunui student'
                if mesaj != '':
                    print(mesaj)
                else:
                    while True:
                        try:
                            idStudent = input('Introduceti id-ul studentului: ') 
                            idDisciplina = input('Introduceti id-ul disciplinei: ')
                            self.__serviceN.stergeNota(idStudent, idDisciplina )
                            break
                        except RepositoryException as mesaj:
                            print(mesaj)
                        except ValidatorException as mesaj:
                            print(mesaj)
                    #Daca datele sunt corecte se va adauga nota
                    print(self.__serviceN.afisazaNote())
            elif ind == 11:
                nr = int(input('Introduceti cati studenti se vor adauga '))
                self.__serviceS.creazaStudenti(nr)
                print(self.__serviceS.afisazaStudenti())
            elif ind == 12:
                nr = int(input('Introduceti cate discipline se vor adauga '))
                self.__serviceD.creazaDiscipline(nr)
                print(self.__serviceD.afisazaDiscipline())
            elif ind == 13:
                if len(self.__repoN.getListaNote()) == 0:
                    print('    Nu exista note acordate niciunui student')
                else:
                    self.__repoNoteDTO = RepositoryNoteDisciplineDTO()
                    self.__serviceNoteDTO = ServiceNoteDisciplineDTO(self.__repoS,self.__repoN,self.__repoD,self.__repoNoteDTO)
                    while True:
                        try:
                            idDisciplina = input('Introduceti id-ul Disciplinei: ')
                            self.__serviceNoteDTO.ordDupaNotaSiNume(idDisciplina)
                            break
                        except RepositoryException as mesaj:
                            print(mesaj)
                        except ValidatorException as mesaj:
                            print(mesaj)
                    print(self.__serviceNoteDTO.afisare())
            elif ind == 14:
                if len(self.__repoS.getListaStudenti()) == 0:
                    print('    Nu exista niciun student adaugat')
                else:
                    self.__repoMedieDTO = RepositoryMedieDisciplineDTO()         
                    self.__serviceMedieDTO = ServiceMedieDisciplineDTO(self.__repoS,self.__repoN,self.__repoD,self.__repoMedieDTO)    
                    self.__serviceMedieDTO.ordMedie20()
                    print(self.__serviceMedieDTO.afisare())
            elif ind == 15:
                if len(self.__repoD.getListaDiscipline()) == 0:
                    print('    Nu exista nicio disciplina adaugata')
                else:
                    self.__repoAcordateDTO = RepositoryMedieNoteAcordateDTO()         
                    self.__serviceAcordateDTO = ServiceMedieNoteAcordateDTO(self.__repoS,self.__repoN,self.__repoD,self.__repoAcordateDTO)    
                    self.__serviceAcordateDTO.ordProfesori()
                    print(self.__serviceAcordateDTO.afisare())
            elif ind == 16:
                break
        print('La revedere!')