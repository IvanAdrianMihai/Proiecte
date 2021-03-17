'''
Created on Nov 9, 2019

@author: Ivan Adrian
'''
from Domain import Student, Disciplina, Notare, NoteDisciplinaDTO, RepositoryException, ValidatorException, ValidatorStudent, ValidatorDisciplina, ValidatorNota,MedieDisciplineDTO,MedieNoteAcordateDTO
from Repository import RepositoryStudent,RepositoryDisciplina, RepositoryNote, RepositoryNoteDisciplineDTO, ValidatorRepStudent, ValidatorRepDisciplina, ValidatorRepNota,\
    RepositoryMedieNoteAcordateDTO, RepositoryMedieDisciplineDTO
import random
import unittest
class ServiceStudent:
    '''
    Tip abstract de date pentru ServiceStudent
    Domain: {repoS unde este clasa care retine studentii}
    '''
    def __init__(self,repoS): 
        '''
        Crearea unei noi instante ServiceStudent
        '''
        self.__repoS = repoS
        self.__valS = ValidatorStudent()
        self.__valRepS = ValidatorRepStudent(repoS)
    def getValS(self):
        '''
        Metoda va returna validatorul Studentului
        '''
        return self.__valS
    def getValRepS(self):
        '''
        Metoda va returna validatorul repozitory-ului Student
        '''
        return self.__valRepS
    def adaugaStudent(self,idStudent,nume):
        '''
        Metoda adauga studentul
        idStudent - string reprezentand un numar natural
        nume - string
        '''
        student = Student(idStudent,nume)
        self.__valS.validareStudent(student)
        self.__valRepS.nuExistaIdStudent(idStudent)
        self.__repoS.adauga(student)
    def adaugaStudent2(self,idStudent,nume):
        '''
        Metoda adauga studentul cu numele schimbad daca acest nume mai exista in lista Studentilor
        idStudent - string reprezentand un numar natural
        nume - string != ''
        '''
        student = Student(idStudent,nume)
        self.__valS.validareStudent(student)
        self.__valRepS.nuExistaIdStudent(idStudent)
        self.__repoS.adauga2(student)
    def stergeStudent(self,idStudent):
        '''
        Metoda sterge Studentul cu idStudent
        idStudent - string ce reprezinta numar natural
        '''
        self.__valS.validareIdStudent(idStudent)
        self.__valRepS.existaIdStudent(idStudent)
        self.__repoS.sterge(idStudent)
    def modificaStudent(self,idActual,idNou,numeNou):
        '''
        Metoda modifica studentul avand idActual cu studentul cu idNou si numeNou
        idActual, idNou - string ce reprezinta numar natural
        numeNou - string !=''
        '''
        self.__valS.validareIdStudent(idActual)
        self.__valRepS.existaIdStudent(idActual)
        student = Student(idNou,numeNou)
        self.__valS.validareStudent(student)
        self.__valRepS.validaremodificareStudent(idActual,student)
        self.__repoS.modifica(idActual,student)
    def afisazaStudenti(self):
        '''
        Returneaza un string cu studentii
        '''
        stud = 'Lista studentilor:'
        for el in self.__repoS.getListaStudenti():
            stud = stud + '\n    '
            stud = stud + str(el.getIDStudent())+' '+el.getNume()
        return stud  
    def cautaStudent(self,idStudent):
        '''
        Metoda returneaza numele si id-ul Studentului cautat
        idStudent - string ce reprezinta numar natural
        '''
        self.__valS.validareIdStudent(idStudent)
        self.__valRepS.existaIdStudent(idStudent)
        return self.__repoS.cauta(idStudent)
    def creazaStudenti(self,nr):
        listaNume = ['Andrei','Andreea','Mircea','Florina','Ioana','Petru','Ciprian','Matei','Sergiu','Mihai','Adrian','Constantin','Iulian','Laurentiu']
        lungime = len(listaNume)
        i = 0
        while i != nr:
            while True:
                try:
                    student = Student(str(random.randint(0,1000)),listaNume[random.randint(-1,lungime-1)])
                    self.__valS.validareStudent(student)
                    self.__valRepS.nuExistaIdStudent(student.getIDStudent())
                    break
                except RepositoryException:
                    pass
                except ValidatorException:
                    pass
            self.__repoS.adauga(student)
            i += 1
class ServiceDisciplina():
    '''
    Tip abstract de date pentru ServiceDisciplina
    Domain: {repoD unde este clasa care retin disciplinele}
    '''
    def __init__(self,repoD):
        '''
        Crearea unei noi instante ServiceDisciplina
        '''
        self.__repoD = repoD
        self.__valD = ValidatorDisciplina()
        self.__valRepD = ValidatorRepDisciplina(repoD)
    def getValD(self):
        '''
        Metoda va returna validatorul Disciplinei
        '''
        return self.__valD
    def getValRepD(self):
        '''
        Metoda va returna validatorul repozitory-ului Disciplinei
        '''
        return self.__valRepD
    def adaugaDisciplina(self,idDisciplina,nume,profesor):
        '''
        Metoda adauga disciplina
        idDisciplina - string ce reprezinta numar natural
        nume si profesor - stringuri != ''
        '''
        disciplina = Disciplina(idDisciplina,nume,profesor)
        self.__valD.validareDisciplina(disciplina)
        self.__valRepD.nuExistaIdDisciplina(idDisciplina)
        self.__repoD.adauga(disciplina)
    def stergeDisciplina(self,idDisciplina):
        '''
        Metoda sterge Disciplina cu idDisciplina
        idDisciplina - string ce reprezinta numar natural
        '''
        self.__valD.validareIdDisciplina(idDisciplina)
        self.__valRepD.existaIdDisciplina(idDisciplina)
        self.__repoD.sterge(idDisciplina)
    def modificaDisciplina(self,idActual,idNou,numeNou,profesorNou):
        '''
        Metoda modifica Disciplina AVAND idActual cu discipLina cu idNou, numeNou si profesorNou
        idActual, idNou - string ce reprezinta numar natural
        numeNou - string !=''
        '''
        self.__valD.validareIdDisciplina(idActual)
        self.__valRepD.existaIdDisciplina(idActual)
        disciplina = Disciplina(idNou,numeNou,profesorNou)
        self.__valD.validareDisciplina(disciplina)
        self.__valRepD.validaremodificareDisciplina(idActual,disciplina)
        self.__repoD.modifica(idActual,disciplina)
    def afisazaDiscipline(self):
        '''
        Returneaza un string cu disciplinele
        '''
        disc = 'Lista disciplinelor:'
        for el in self.__repoD.getListaDiscipline():
            disc = disc + '\n    '
            disc = disc + str(el.getIDdisciplina())+' '+ el.getNume() + ' ' + el.getProfesor()
        return disc
    def cautaDisciplina(self,idDisciplina):
        '''
        Metoda returneaza numele, profesorul si id-ul Disciplinei cautat
        idDisciplinei - string ce reprezinta numar natural
        '''
        self.__valD.validareIdDisciplina(idDisciplina)
        self.__valRepD.existaIdDisciplina(idDisciplina)
        return self.__repoD.cauta(idDisciplina)
    def creazaDiscipline(self,nr):
        listaProfesori = ['Andrei','Andreea','Mircea','Florina','Ioana','Petru','Ciprian','Matei','Sergiu','Mihai','Adrian','Constantin','Iulian','Laurentiu']
        lungimeP = len(listaProfesori)
        listaNume = ['matematica','chimie','fizica','istorie','informatica','TIC','EDA','Logica','Romana']
        lungimeN = len(listaNume)
        i = 0
        while i != nr:
            while True:
                try:
                    disciplina = Disciplina(str(random.randint(0,1000)),listaNume[random.randint(-1,lungimeN-1)],listaProfesori[random.randint(-1,lungimeP-1)])
                    self.__valD.validareDisciplina(disciplina)
                    self.__valRepD.nuExistaIdDisciplina(disciplina.getIDdisciplina())
                    break
                except ValidatorException:
                    pass
                except RepositoryException:
                    pass
            self.__repoD.adauga(disciplina)
            i += 1
class ServiceNota():
    '''
    Tip abstract de date pentru ServiceNota
    Domain: {repoS, repoD, repoN unde sunt clase care retin studentii, disciplinele si asignarea de note
            servS,servD sunt instante ale claselor ServiceStudent,serviceDisciplina}
    '''
    def __init__(self,repoN,repoS,repoD,servS,servD):
        '''
        Crearea unei noi instante ServiceNota
        '''
        self.__repoN = repoN
        self.__repoS = repoS
        self.__repoD = repoD
        self.__servS = servS
        self.__servD = servD
        self.__valN = ValidatorNota()
        self.__valRepN = ValidatorRepNota(repoS,repoD,repoN)
    def asignareNota(self,idStudent,idDisciplina,nota):
        '''
        Metoda asigneaza nota lui idStudent la idDisciplina
        nota -  string ce reprezinta numar real din [1,10]
        idStudent,idDisciplina - string ce reprezinta numar natural
        '''
        self.__valN.notareCorecta(idStudent,idDisciplina,nota)  
        self.__valRepN.existaStudentulSiDisciplina(idStudent, idDisciplina)
        self.__valRepN.nuExistaNota(idStudent, idDisciplina)
        notare = Notare(self.__repoS.cauta(idStudent),self.__repoD.cauta(idDisciplina),nota)     
        self.__repoN.adaugareNota(notare)
    def stergeNota(self,idStudent,idDisciplina):
        '''
        Metoda sterge nota studentului idStudent la disciplina idDisciplina
        idStudent,idDisciplina - string ce reprezinta numar natural
        ''' 
        self.__valN.notareCorecta(idStudent, idDisciplina, '9')
        self.__valRepN.existaStudentulSiDisciplina(idStudent, idDisciplina)
        self.__valRepN.existaNota(idStudent, idDisciplina)
        self.__repoN.stergereNota(idStudent, idDisciplina)
    def afisazaNote(self):
        '''
        Metoda afisaza lista cu notele acordate
        '''
        note = 'Lista notelor acordate:'
        for el in self.__repoN.getListaNote():
            note = note + '\n    Studentul ' + el.getStudent().getNume() + ' cu id-ul ' + el.getStudent().getIDStudent() + ' la Disciplina ' + el.getDisciplina().getNume() + ' predata de ' + el.getDisciplina().getProfesor() + ' cu id-ul ' + el.getDisciplina().getIDdisciplina() +' a primit nota ' + el.getNota()
        return note
class ServiceNoteDisciplineDTO():
    '''
    Tip abstract de date pentru ServiceNoteDiciplineDTO
    Domain: {repoS, repoD, repoN unde sunt clase care retin studentii, disciplinele si asignarea de note
            servS,servD sunt instante ale claselor ServiceStudent,serviceDisciplina}
    '''
    def __init__(self,repoS,repoN,repoD,repoDTO):
        '''
        Crearea unei noi instante ServiceNoteDisciplineDTO
        '''
        self.__repoN = repoN
        self.__repoD = repoD
        self.__repoS =repoS
        self.__valD = ValidatorDisciplina()
        self.__valRepD = ValidatorRepDisciplina(repoD)
        self.__repoDTO = repoDTO
    def adaugaStudentii(self,idDisciplina):
        '''
        Metoda adauga in lista NoteDisciplineDTO toti studentii care au note la disciplina cu idDisciplina
        '''
        for el in self.__repoN.getListaNote():
            if el.getDisciplina().getIDdisciplina() == idDisciplina:
                studentDTO = NoteDisciplinaDTO(el.getStudent().getNume(),el.getNota())
                self.__repoDTO.adaugaStudent(studentDTO)
    def ordDupaNotaSiNume(self,idDisciplina):
        '''
        Metoda ordoneaza dupa nota obiectele din lista NoteDisciplina
        '''
        self.__valD.validareIdDisciplina(idDisciplina)
        self.__valRepD.existaIdDisciplina(idDisciplina)
        self.adaugaStudentii(idDisciplina)
        ok = False
        while ok==False:
            ok=True
            lista = self.__repoDTO.getListaNoteDisciplina()
            for el in range(0,len(lista)-1):
                if (float(lista[el].getNota()) < float(lista[el+1].getNota())) or (float(lista[el].getNota()) == float(lista[el+1].getNota()) and lista[el].getNume() > lista[el+1].getNume()):
                    aux = NoteDisciplinaDTO('','')
                    aux.setNume(lista[el].getNume())
                    aux.setNota(lista[el].getNota())
                    lista[el].setNume(lista[el+1].getNume())
                    lista[el].setNota(lista[el+1].getNota())
                    lista[el+1].setNume(aux.getNume())
                    lista[el+1].setNota(aux.getNota())
                    ok = False
    def afisare(self):
        '''
        Metoda afisaza lista studentilor si a notelor acestora
        '''
        mesaj = 'Lista notelor elevilor la aceasta disciplina este:'
        for el in self.__repoDTO.getListaNoteDisciplina():
            mesaj = mesaj + '\n    ' + el.getNume() + ' ' + el.getNota()
        return mesaj 
class ServiceMedieDisciplineDTO():
    '''
    Tip abstract de date pentru ServiceMedieDisciplineDTO
    Domain: {repoS, repoD, repoN unde sunt clase care retin studentii, disciplinele si asignarea de note
            servS,servD sunt instante ale claselor ServiceStudent,serviceDisciplina}
    '''
    def __init__(self,repoS,repoN,repoD,repoDTO):
        '''
        Crearea unei noi instante ServiceMedieDisciplineDTO
        '''
        self.__repoN = repoN
        self.__repoD = repoD
        self.__repoS =repoS
        self.__valD = ValidatorDisciplina()
        self.__valRepD = ValidatorRepDisciplina(repoD)
        self.__repoDTO = repoDTO
    def adaugaMediaStudenti(self):
        '''
        Metoda adauga in lista NoteDisciplineDTO studentii cu media acestora la toate disiplinele
        '''
        for i in self.__repoS.getListaStudenti():
            nota = 0
            nr = 0 
            for el in self.__repoN.getListaNote():
                if el.getStudent().getIDStudent() == i.getIDStudent():
                    nr += 1
                    nota = nota + float(el.getNota())
            if nr != 0:
                nota = nota/nr
            self.__repoDTO.adaugaMedie(MedieDisciplineDTO(i.getNume(),str(nota)))
    def ordMedie20(self):
        '''
        Metoda retine in RepositoryNoteDiscipline primii 20 dintre studenti cu media cea mai mare la toate disciplinele
        '''
        self.adaugaMediaStudenti()
        lista = self.__repoDTO.getListaMedieDisciplineDTO()
        ok = False
        while ok==False:
            ok=True
            for el in range(0,len(lista)-1):
                if float(lista[el].getMedia()) < float(lista[el+1].getMedia()):
                    aux = MedieDisciplineDTO('','')
                    aux.setNume(lista[el].getNume())
                    aux.setMedia(lista[el].getMedia())
                    lista[el].setNume(lista[el+1].getNume())
                    lista[el].setMedia(lista[el+1].getMedia())
                    lista[el+1].setNume(aux.getNume())
                    lista[el+1].setMedia(aux.getMedia())
                    ok = False
        nr = int(0.2 * len(lista))
        while nr != len(lista):
            lista.pop(len(lista)-1)
    def afisare(self):
        '''
        Metoda afisaza lista studentilor si a notelor acestora
        '''
        mesaj = 'Lista celor 20% dintre studenti este:'
        for el in self.__repoDTO.getListaMedieDisciplineDTO():
            mesaj = mesaj + '\n    ' + el.getNume() + ' ' + el.getMedia()
        return mesaj 
class ServiceMedieNoteAcordateDTO():
    '''
    Tip abstract de date pentru ServiceMedieNoteAcordateDTO
    Domain: {repoS, repoD, repoN unde sunt clase care retin studentii, disciplinele si asignarea de note
            servS,servD sunt instante ale claselor ServiceStudent,serviceDisciplina}
    '''
    def __init__(self,repoS,repoN,repoD,repoDTO):
        '''
        Crearea unei noi instante ServiceMedieNoteAcordateDTO
        '''
        self.__repoN = repoN
        self.__repoD = repoD
        self.__repoS =repoS
        self.__valD = ValidatorDisciplina()
        self.__valRepD = ValidatorRepDisciplina(repoD)
        self.__repoDTO = repoDTO
    def adaugaMediaProfesori(self):
        '''
        Metoda adauga in lista NoteDisicplineDTO profesorii cu media notelor acordate de catre acestia
        '''
        for i in self.__repoD.getListaDiscipline():
            ok = True
            for el in self.__repoDTO.getListaMedieNoteAcordateDTO():
                if el.getNume() == i.getProfesor():
                    ok = False
            if ok:
                nota = 0
                nr = 0
                for el in self.__repoN.getListaNote():
                    if i.getProfesor() == el.getDisciplina().getProfesor():
                        nr += 1
                        nota = nota + float(el.getNota())
                if nr != 0:
                    nota = nota/nr
                self.__repoDTO.adaugaMedie(MedieNoteAcordateDTO(i.getProfesor(),str(nota)))
    def ordProfesori(self):
        '''
        Metoda retine in RepositoryNoteDiscipline lista profesorilor ordonati crescator dupa media notelor acordate de acestia 
        '''
        self.adaugaMediaProfesori()
        lista = self.__repoDTO.getListaMedieNoteAcordateDTO()
        ok = False
        while ok==False:
            ok=True
            for el in range(0,len(lista)-1):
                if float(lista[el].getMedia()) > float(lista[el+1].getMedia()):
                    aux = MedieNoteAcordateDTO('','')
                    aux.setNume(lista[el].getNume())
                    aux.setMedia(lista[el].getMedia())
                    lista[el].setNume(lista[el+1].getNume())
                    lista[el].setMedia(lista[el+1].getMedia())
                    lista[el+1].setNume(aux.getNume())
                    lista[el+1].setMedia(aux.getMedia())
                    ok = False
    def afisare(self):
        '''
        Metoda afisaza lista studentilor si a notelor acestora
        '''
        mesaj = 'Lista profesorilor este:'
        for el in self.__repoDTO.getListaMedieNoteAcordateDTO():
            mesaj = mesaj + '\n    ' + el.getNume() + ' ' + el.getMedia()
        return mesaj 
class TestController(unittest.TestCase):
    def test_adaugaStudent(self):
        repozitS = RepositoryStudent()
        service = ServiceStudent(repozitS)
        with self.assertRaises(ValidatorException):
            service.adaugaStudent(-2, 'Penescu Marian')   
        with self.assertRaises(ValidatorException):
            service.adaugaStudent(2, '')
        with self.assertRaises(ValidatorException):
            service.adaugaStudent(-2, '') 
        service.adaugaStudent(2, 'Penescu Marian')
        lista = repozitS.getListaStudenti()
        self.assertTrue( lista[0].getIDStudent() == 2)
        self.assertTrue( lista[0].getNume() == 'Penescu Marian')
    def test_adaugaStudent2(self):
        repozitS = RepositoryStudent()
        service = ServiceStudent(repozitS)
        service.adaugaStudent2(1, 'Mircea')
        service.adaugaStudent2(3, 'Mircea')
        lista = repozitS.getListaStudenti()
        self.assertTrue( lista[1].getNume() == 'Mircea(2)')
    def test_stergeStudent(self):
        repozitS = RepositoryStudent()
        service = ServiceStudent(repozitS)
        service.adaugaStudent(2, 'Ivan Marian')
        service.adaugaStudent(3, 'Ivan Adrian')
        with self.assertRaises(RepositoryException):
            service.stergeStudent(4)
        service.stergeStudent(2)
        lista = repozitS.getListaStudenti()
        self.assertTrue( len(lista) == 1)
        self.assertTrue( lista[0].getIDStudent() == 3)
        self.assertTrue( lista[0].getNume() == 'Ivan Adrian') 
    def test_modificaStudent(self):
        repozitS = RepositoryStudent()
        service = ServiceStudent(repozitS)
        service.adaugaStudent(2, 'Ivan Marian')
        service.adaugaStudent(3, 'Ivan Adrian')
        service.modificaStudent(2,2,'Alexandru')
        lista = repozitS.getListaStudenti()
        self.assertTrue( lista[0].getIDStudent() == 2)
        self.assertTrue( lista[0].getNume() == 'Alexandru') 
    def test_cautaStudent(self):
        repozitS = RepositoryStudent()
        service = ServiceStudent(repozitS)
        service.adaugaStudent(1, 'Petru')
        service.adaugaStudent(2, 'Adi')
        student = repozitS.cauta(2)
        self.assertTrue( student.getIDStudent() == 2)
    def test_creazaStudenti(self):
        repozitS = RepositoryStudent()
        service = ServiceStudent(repozitS)
        service.creazaStudenti(5)
        lista = repozitS.getListaStudenti()
        self.assertTrue( len(lista) == 5)
    def test_adaugaDisciplina(self):
        repozitD = RepositoryDisciplina()
        service = ServiceDisciplina(repozitD)
        with self.assertRaises(ValidatorException):
            service.adaugaDisciplina(-2,'mate','Penescu Marian') 
        with self.assertRaises(ValidatorException):
            service.adaugaDisciplina(2,'','Penescu Marian')   
        with self.assertRaises(ValidatorException):
            service.adaugaDisciplina(2,'mate','')  
        service.adaugaDisciplina(2,'matematica','Penescu Marian')
        lista = repozitD.getListaDiscipline()
        self.assertTrue( lista[0].getIDdisciplina() == 2)
        self.assertTrue( lista[0].getProfesor() == 'Penescu Marian')
        self.assertTrue( lista[0].getNume() == 'matematica')
    def test_stergeDisciplina(self):
        repozitD = RepositoryDisciplina()
        service = ServiceDisciplina(repozitD)
        service.adaugaDisciplina(1, 'MATE', 'Andone Cristina')
        service.adaugaDisciplina(4, 'ROMANA', 'Copaceanu Adriana')
        with self.assertRaises(RepositoryException):
            service.stergeDisciplina(2)
        service.stergeDisciplina(1)
        lista = repozitD.getListaDiscipline()
        self.assertTrue( len(lista) == 1)
        self.assertTrue( lista[0].getIDdisciplina() == 4)
        self.assertTrue( lista[0].getNume() == 'ROMANA')
        self.assertTrue( lista[0].getProfesor() == 'Copaceanu Adriana')   
    def test_modificaDisciplina(self):
        repozitD = RepositoryDisciplina()
        service = ServiceDisciplina(repozitD)
        service.adaugaDisciplina(1, 'MATE', 'Andone Cristina')
        service.adaugaDisciplina(4, 'ROMANA', 'Copaceanu Adriana')
        service.modificaDisciplina(1, 5, 'chimie', 'Laslau')
        lista = repozitD.getListaDiscipline()
        self.assertTrue( lista[0].getIDdisciplina() == 5)
        self.assertTrue( lista[0].getNume() == 'chimie')
        self.assertTrue( lista[0].getProfesor() == 'Laslau') 
    def test_cautaDisciplina(self):
        repozitD = RepositoryDisciplina()
        service = ServiceDisciplina(repozitD)
        service.adaugaDisciplina(1,'mate', 'Petru')
        service.adaugaDisciplina(2,'chimie', 'Adi')
        disciplina = repozitD.cauta(2)
        self.assertTrue( disciplina.getProfesor() == 'Adi')
    def test_creazaDiscipline(self):
        repozitD = RepositoryDisciplina()
        service = ServiceDisciplina(repozitD)
        service.creazaDiscipline(5)
        lista = repozitD.getListaDiscipline()
        self.assertTrue( len(lista) == 5)
    def test_asignareNota(self):
        repozitN = RepositoryNote()
        repozitS = RepositoryStudent()
        repozitD = RepositoryDisciplina()
        serviceS = ServiceStudent(repozitS)
        serviceD = ServiceDisciplina(repozitD)
        serviceN = ServiceNota(repozitN,repozitS,repozitD,serviceS,serviceD)
        serviceD.adaugaDisciplina(1,'mate', 'Petru')
        serviceS.adaugaStudent(2, 'Adi')
        serviceN.asignareNota(2, 1, '10')
        lista = repozitN.getListaNote()
        self.assertTrue( lista[0].getStudent().getIDStudent() == 2)
        self.assertTrue( lista[0].getDisciplina().getIDdisciplina() == 1)
        self.assertTrue( lista[0].getNota() == '10')
    def test_stergeNota(self):
        repozitN = RepositoryNote()
        repozitS = RepositoryStudent()
        repozitD = RepositoryDisciplina()
        serviceS = ServiceStudent(repozitS)
        serviceD = ServiceDisciplina(repozitD)
        serviceN = ServiceNota(repozitN,repozitS,repozitD,serviceS,serviceD)
        serviceD.adaugaDisciplina(1,'mate', 'Petru')
        serviceD.adaugaDisciplina(2,'chimie', 'Andreea')
        serviceS.adaugaStudent(2, 'Adi')
        serviceS.adaugaStudent(1, 'Teo')
        serviceN.asignareNota(2, 1, '10')
        serviceN.asignareNota(1, 2, '10')
        serviceN.stergeNota(2, 1)
        lista = repozitN.getListaNote()
        self.assertTrue( lista[0].getStudent().getIDStudent() == 1)
        self.assertTrue( lista[0].getDisciplina().getIDdisciplina() == 2)
        self.assertTrue( lista[0].getNota() == '10')
    def test_adaugaStudeni(self):
        repozitN = RepositoryNote()
        repozitS = RepositoryStudent()
        repozitD = RepositoryDisciplina()
        repozitDTO = RepositoryNoteDisciplineDTO()
        serviceS = ServiceStudent(repozitS)
        serviceD = ServiceDisciplina(repozitD)
        serviceN = ServiceNota(repozitN,repozitS,repozitD,serviceS,serviceD)
        serviceDTO = ServiceNoteDisciplineDTO(repozitS,repozitN,repozitD,repozitDTO)
        serviceD.adaugaDisciplina(1,'mate', 'Petru')
        serviceD.adaugaDisciplina(2,'romana', 'Alin')
        serviceD.adaugaDisciplina(3,'istorie', 'Bacila')
        serviceS.adaugaStudent(2, 'Adi')
        serviceS.adaugaStudent(4, 'Rares')
        serviceS.adaugaStudent(6, 'Robert')
        serviceN.asignareNota(2, 1, '10')
        serviceN.asignareNota(2, 2, '9')
        serviceN.asignareNota(4, 1, '8')
        serviceN.asignareNota(6, 1, '6')
        serviceN.asignareNota(4, 2, '10')
        serviceDTO.adaugaStudentii(1)
        lista = repozitDTO.getListaNoteDisciplina()
        self.assertTrue( len(lista) == 3)
        self.assertTrue( lista[0].getNume() == 'Adi')
        self.assertTrue( lista[1].getNota() == '8')
        self.assertTrue( lista[2].getNume() == 'Robert')
    def test_adaugaMediaProfesori(self):
        repozitN = RepositoryNote()
        repozitS = RepositoryStudent()
        repozitD = RepositoryDisciplina()
        repozitDTO = RepositoryMedieNoteAcordateDTO()
        serviceS = ServiceStudent(repozitS)
        serviceD = ServiceDisciplina(repozitD)
        serviceN = ServiceNota(repozitN,repozitS,repozitD,serviceS,serviceD)
        serviceDTO = ServiceMedieNoteAcordateDTO(repozitS,repozitN,repozitD,repozitDTO)
        serviceD.adaugaDisciplina(1,'mate', 'Petru')
        serviceD.adaugaDisciplina(2,'romana', 'Alin')
        serviceD.adaugaDisciplina(3,'istorie', 'Bacila')
        serviceD.adaugaDisciplina(4, 'algebra', 'Petru')
        serviceS.adaugaStudent(2, 'Adi')
        serviceS.adaugaStudent(4, 'Rares')
        serviceS.adaugaStudent(6, 'Robert')
        serviceN.asignareNota(2, 1, '10')
        serviceN.asignareNota(2, 2, '9')
        serviceN.asignareNota(4, 1, '8')
        serviceN.asignareNota(6, 1, '6')
        serviceN.asignareNota(4, 2, '10')
        serviceN.asignareNota(4, 4, '4')
        serviceDTO.adaugaMediaProfesori()
        lista = repozitDTO.getListaMedieNoteAcordateDTO()
        self.assertTrue( len(lista) == 3)
        self.assertTrue( lista[0].getNume() == 'Petru')
        self.assertTrue( lista[0].getMedia() == '7.0')
        self.assertTrue( lista[1].getNume() == 'Alin')
        self.assertTrue( lista[1].getMedia() == '9.5')
        self.assertTrue( lista[2].getNume() == 'Bacila')
        self.assertTrue( lista[2].getMedia() == '0')
    def test_ordDupaNotaSiNume(self):
        repozitN = RepositoryNote()
        repozitS = RepositoryStudent()
        repozitD = RepositoryDisciplina()
        repozitDTO = RepositoryNoteDisciplineDTO()
        serviceS = ServiceStudent(repozitS)
        serviceD = ServiceDisciplina(repozitD)
        serviceN = ServiceNota(repozitN,repozitS,repozitD,serviceS,serviceD)
        serviceDTO = ServiceNoteDisciplineDTO(repozitS,repozitN,repozitD,repozitDTO)
        serviceD.adaugaDisciplina('1','mate', 'Petru')
        serviceD.adaugaDisciplina('2','romana', 'Alin')
        serviceD.adaugaDisciplina('3','istorie', 'Bacila')
        serviceS.adaugaStudent('2', 'Marius')
        serviceS.adaugaStudent('4', 'Adi')
        serviceS.adaugaStudent('6', 'Robert')
        serviceN.asignareNota('2', '1', '10')
        serviceN.asignareNota('2', '2', '9')
        serviceN.asignareNota('4', '1', '8')
        serviceN.asignareNota('6', '1', '6')
        serviceN.asignareNota('4', '2', '10')
        serviceDTO.ordDupaNotaSiNume('1')
        lista = repozitDTO.getListaNoteDisciplina()
        self.assertTrue( len(lista) == 3)
        self.assertTrue( lista[0].getNume() == 'Marius')
        self.assertTrue( lista[1].getNota() == '8')
        self.assertTrue( lista[2].getNume() == 'Robert')
    def test_ordMedie20(self):
        repozitN = RepositoryNote()
        repozitS = RepositoryStudent()
        repozitD = RepositoryDisciplina()
        repozitDTO = RepositoryMedieDisciplineDTO()
        serviceS = ServiceStudent(repozitS)
        serviceD = ServiceDisciplina(repozitD)
        serviceN = ServiceNota(repozitN,repozitS,repozitD,serviceS,serviceD)
        serviceDTO = ServiceMedieDisciplineDTO(repozitS,repozitN,repozitD,repozitDTO)
        serviceD.adaugaDisciplina(1,'mate', 'Petru')
        serviceD.adaugaDisciplina(2,'romana', 'Alin')
        serviceD.adaugaDisciplina(3,'istorie', 'Bacila')
        serviceS.adaugaStudent(6, 'Marius')
        serviceS.adaugaStudent(4, 'Adi')
        serviceS.adaugaStudent(2, 'Robert')
        serviceS.adaugaStudent(8, 'Mircea')
        serviceS.adaugaStudent(10, 'Rares')
        serviceN.asignareNota(2, 1, '10')
        serviceN.asignareNota(2, 2, '9')
        serviceN.asignareNota(4, 1, '8')
        serviceN.asignareNota(6, 1, '6')
        serviceN.asignareNota(4, 2, '10')
        serviceDTO.ordMedie20()
        lista = repozitDTO.getListaMedieDisciplineDTO()
        self.assertTrue( len(lista) == 1)
        self.assertTrue( lista[0].getMedia() == '9.5')
        self.assertTrue( lista[0].getNume() == 'Robert')
    def test_adaugaMediaStudenti(self):
        repozitN = RepositoryNote()
        repozitS = RepositoryStudent()
        repozitD = RepositoryDisciplina()
        repozitDTO = RepositoryMedieDisciplineDTO()
        serviceS = ServiceStudent(repozitS)
        serviceD = ServiceDisciplina(repozitD)
        serviceN = ServiceNota(repozitN,repozitS,repozitD,serviceS,serviceD)
        serviceDTO = ServiceMedieDisciplineDTO(repozitS,repozitN,repozitD,repozitDTO)
        serviceD.adaugaDisciplina(1,'mate', 'Petru')
        serviceD.adaugaDisciplina(2,'romana', 'Alin')
        serviceD.adaugaDisciplina(3,'istorie', 'Bacila')
        serviceS.adaugaStudent(2, 'Marius')
        serviceS.adaugaStudent(4, 'Adi')
        serviceS.adaugaStudent(6, 'Robert')
        serviceS.adaugaStudent(8, 'Mircea')
        serviceS.adaugaStudent(10, 'Rares')
        serviceN.asignareNota(2, 1, '10')
        serviceN.asignareNota(2, 2, '9')
        serviceN.asignareNota(4, 1, '8')
        serviceN.asignareNota(6, 1, '6')
        serviceN.asignareNota(4, 2, '10')
        serviceDTO.adaugaMediaStudenti()
        lista = repozitDTO.getListaMedieDisciplineDTO()
        self.assertTrue( len(lista) == 5)
        self.assertTrue( lista[0].getMedia() == '9.5')
        self.assertTrue( lista[1].getMedia() == '9.0')
        self.assertTrue( lista[2].getMedia() == '6.0')
        self.assertTrue( lista[3].getMedia() == '0')
        self.assertTrue( lista[4].getMedia() == '0')
    def test_ordProfesori(self):
        repozitN = RepositoryNote()
        repozitS = RepositoryStudent()
        repozitD = RepositoryDisciplina()
        repozitDTO = RepositoryMedieNoteAcordateDTO()
        serviceS = ServiceStudent(repozitS)
        serviceD = ServiceDisciplina(repozitD)
        serviceN = ServiceNota(repozitN,repozitS,repozitD,serviceS,serviceD)
        serviceDTO = ServiceMedieNoteAcordateDTO(repozitS,repozitN,repozitD,repozitDTO)
        serviceD.adaugaDisciplina(1,'mate', 'Petru')
        serviceD.adaugaDisciplina(2,'romana', 'Alin')
        serviceD.adaugaDisciplina(3,'istorie', 'Bacila')
        serviceD.adaugaDisciplina(4, 'algebra', 'Petru')
        serviceS.adaugaStudent(2, 'Adi')
        serviceS.adaugaStudent(4, 'Rares')
        serviceS.adaugaStudent(6, 'Robert')
        serviceN.asignareNota(2, 1, '10')
        serviceN.asignareNota(2, 2, '9')
        serviceN.asignareNota(4, 1, '8')
        serviceN.asignareNota(6, 1, '6')
        serviceN.asignareNota(4, 2, '10')
        serviceN.asignareNota(4, 4, '4')
        serviceDTO.ordProfesori()
        lista = repozitDTO.getListaMedieNoteAcordateDTO()
        self.assertTrue( len(lista) == 3)
        self.assertTrue( lista[0].getNume() == 'Bacila')
        self.assertTrue( lista[0].getMedia() == '0')
        self.assertTrue( lista[1].getNume() == 'Petru')
        self.assertTrue( lista[1].getMedia() == '7.0')
        self.assertTrue( lista[2].getNume() == 'Alin')
        self.assertTrue( lista[2].getMedia() == '9.5')
    def test_afisari(self):  
        repozitN = RepositoryNote()
        repozitS = RepositoryStudent()
        repozitD = RepositoryDisciplina()
        repozitNoteDTO = RepositoryNoteDisciplineDTO()
        repozitMedieDTO = RepositoryMedieDisciplineDTO()
        repozitAcordateDTO = RepositoryMedieNoteAcordateDTO()
        serviceS = ServiceStudent(repozitS)
        serviceD = ServiceDisciplina(repozitD)
        serviceN = ServiceNota(repozitN,repozitS,repozitD,serviceS,serviceD)
        serviceNoteDTO = ServiceNoteDisciplineDTO(repozitS,repozitN,repozitD,repozitNoteDTO)
        serviceMedieDTO =ServiceMedieDisciplineDTO(repozitS,repozitN,repozitD,repozitMedieDTO)
        serviceAcordateDTO =ServiceMedieNoteAcordateDTO(repozitS,repozitN,repozitD,repozitAcordateDTO)
        self.assertTrue( serviceD.afisazaDiscipline() == 'Lista disciplinelor:')
        self.assertTrue( serviceS.afisazaStudenti() == 'Lista studentilor:')   
        self.assertTrue( serviceN.afisazaNote() == 'Lista notelor acordate:')
        self.assertTrue( serviceNoteDTO.afisare() == 'Lista notelor elevilor la aceasta disciplina este:')
        self.assertTrue( serviceMedieDTO.afisare() == 'Lista delor 20% dintre studenti este:')
        self.assertTrue( serviceAcordateDTO.afisare() == 'Lista profesorilor este:')
if __name__ == '__main__':
    unittest.main()