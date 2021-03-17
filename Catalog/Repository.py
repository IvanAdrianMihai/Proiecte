'''
Created on Nov 11, 2019

@author: Ivan Adrian
'''
from Domain import Student,Disciplina,Notare, NoteDisciplinaDTO, RepositoryException, MedieDisciplineDTO,MedieNoteAcordateDTO
import unittest
class RepositoryStudent:
    '''
    Tip abstract de date pentru retinerea Studentilor
    '''
    def __init__(self):
        '''
        Crearea unei noi instante RepositoryStudent
        '''
        self.__listaStudenti = []
    def getListaStudenti(self):
        '''
        Metoda returneaza listaStudentilor
        '''
        return self.__listaStudenti
    def adauga(self,student):
        '''
        Metoda adauga in listaStudentilor studentul
        student - instanta a clasei student
        '''
        self.getListaStudenti().append(student)
    def adauga2(self,student):
        '''
        Metoda adauga in listaStudentilor studentul modificat daca acesta are un nume care mai apare in lista
        student - instanta a clasei student
        '''
        acelasiNume = 0
        for el in self.getListaStudenti():
            nume = el.getNume().split("(")
            if nume[0] == student.getNume():
                acelasiNume += 1
        if acelasiNume != 0:
            acelasiNume += 1
            student.setNume(student.getNume() + '(' + str(acelasiNume) + ')')
        self.getListaStudenti().append(student)
    def sterge(self,idStudent):
        '''
        Metoda sterge Studentul cu idStudent din listaStudent
        idStudent - string ce reprezinta numar natural        
        '''
        lista = self.getListaStudenti()
        i = 0
        while True:
            if lista[i].getIDStudent() == idStudent:
                lista.pop(i)
                break
            i += 1
    def modifica(self,idActual,student):
        '''
        Metoda modifica Studentul cu idActual cu student
        student - instanta a clasei student
        idActual - string ce reprezinta numar natural        
        '''
        lista = self.getListaStudenti()
        i = 0
        while True:
            if lista[i].getIDStudent() == idActual:
                lista[i].setIDStudent(student.getIDStudent())
                lista[i].setNume(student.getNume()) 
                break
            i += 1
    def cauta(self,idStudent):
        '''
        Metoda cauta studentul cu idStudent si returneaza un string cu numele si id acestuia
        idStudent - string ce reprezinta numar natural        
        '''
        lista = self.getListaStudenti()
        i = 0
        while True:
            if lista[i].getIDStudent() == idStudent:
                student = lista[i]
                break
            i += 1
        return student
class RepositoryStudent2():
    '''
    Tip abstract de date pentru retinerea Studentilor
    '''
    def __init__(self,fisier):
        '''
        Crearea unei noi instante RepositoryStudent2
        '''
        self.__fisier = fisier
    def getListaStudenti(self):
        '''
        Metoda returneaza listaStudentilor
        '''
        lista =[]
        with open(self.__fisier) as fh:
            for linie in fh:
                lin = linie.strip()
                if lin != '':
                    t = lin.split(',')
                    student = Student(t[0],t[1])
                    lista.append(student)
        return lista
    def getFisier(self):
        '''
        Metoda returneaza fisierul
        '''
        return self.__fisier
    def adauga(self,student):
        '''
        Metoda adauga in fisier studentul
        student - instanta a clasei student
        '''
        f=open(self.__fisier,'a')
        f.write('\n'+student.getIDStudent()+','+student.getNume())
        f.close()
    def sterge(self,idStudent):
        '''
        Metoda sterge Studentul cu idStudent din fisier
        idStudent - string ce reprezinta numar natural        
        '''
        g = open('dateAux.txt','w')
        with open(self.__fisier) as fh:
            for linie in fh:
                lin = linie.strip()
                if lin != '':
                    t = lin.split(',')
                    if t[0] != idStudent:
                        g.write(linie)
        g.close()
        f = open(self.__fisier,'w')
        with open('dateAux.txt') as fh:
            for linie in fh:
                lin = linie.strip()
                if lin != '':
                    f.write(linie)
        f.close()
    def modifica(self,idActual,student):
        '''
        Metoda modifica Studentul cu idActual cu student in fisier
        student - instanta a clasei student
        idActual - string ce reprezinta numar natural        
        '''
        g = open('dateAux.txt','w')
        with open(self.__fisier) as fh:
            for linie in fh:
                lin = linie.strip()
                if lin != '':
                    t = lin.split(',')
                    if t[0] != idActual:
                        g.write('\n'+lin)
                    else:
                        g.write('\n'+student.getIDStudent()+','+student.getNume())
        g.close()
        f = open(self.__fisier,'w')
        with open('dateAux.txt') as fh:
            for linie in fh:
                lin = linie.strip()
                if lin != '':
                    f.write(linie)
        f.close()
    def cauta(self,idStudent):
        '''
        Metoda cauta studentul cu idStudent si returneaza un string cu numele si id acestuia
        idStudent - string ce reprezinta numar natural        
        '''
        student = Student('','')
        with open(self.__fisier) as fh:
            for linie in fh:
                lin = linie.strip()
                if lin != '':
                    t = lin.split(',')
                    if t[0] == idStudent:
                        student.setIDStudent(t[0])
                        student.setNume(t[1])
        return student
class RepositoryStudentFisier(RepositoryStudent):
    '''
    Tip abstract de date pentru retinerea studetilor in fisiere
    '''
    def __init__(self,numeFisier):
        '''
        Crearea unei noi instante care mosteneste instanta RepositoryStudent
        '''
        RepositoryStudent.__init__(self)
        self.__numeFisier = numeFisier
        self.citireFisier(numeFisier)
    def citireFisier(self,numeFisier):
        '''
        Introducerea datelor din fisier in program
        '''
        with open(numeFisier) as fh:
            for linie in fh:
                lin = linie.strip()
                if lin != '':
                    t = lin.split(',')
                    RepositoryStudent.adauga(self,Student(t[0],t[1]))
    def stocareFisier(self,numeFisier):
        '''
        Actualizarea fisierului cu datele din program
        '''
        f = open(numeFisier,'w')
        lista = self.getListaStudenti()
        lungime = len(lista)
        if lungime >= 1:
            f.write(lista[0].getIDStudent()+','+lista[0].getNume())
        for i in range(1,lungime):
            f.write('\n'+lista[i].getIDStudent()+','+lista[i].getNume())
        f.close()
    def adauga(self, student):
        '''
        Aceeasi metoda adauga la finalul careia datele se actualizeaza in fisier
        '''
        RepositoryStudent.adauga(self, student)
        self.stocareFisier(self.__numeFisier)
    def sterge(self, idStudent):
        '''
        Aceeasi metoda sterge la finalul careia datele se actualizeaza in fisier
        '''
        RepositoryStudent.sterge(self, idStudent)
        self.stocareFisier(self.__numeFisier)
    def modifica(self, idActual, student):
        '''
        Aceeasi metoda modifica la finalul careia datele se actualizeaza in fisier
        '''
        RepositoryStudent.modifica(self, idActual, student)
        self.stocareFisier(self.__numeFisier)
class ValidatorRepStudent():
    '''
    Tip abstract de date ce defineste un validator pentru Studenti
    Domain: {repo este clasa in care se retin Studentii}
    '''
    def __init__(self,repo):
        '''
        Crearea unei noi instante ValidatorRepStudent
        '''
        self.__repo = repo
    def existaIdStudent(self,idStudent):
        '''
        Metoda valideaza si verifica daca exista idStudent
        idStudent - string ce reprezinta un numar natural
        Arunca exceptie daca idStudentului nu exista studentul cu idStudent in lista
        '''
        ok = False
        for el in self.__repo.getListaStudenti():
            if el.getIDStudent() == idStudent:
                ok = True
        if ok == False:
            raise RepositoryException('    Nu exista studentul cu acest id')
    def nuExistaIdStudent(self,idStudent):
        '''
        Metoda verifica daca nu exista studentul cu idStudent
        idStudent -string ce reprezinta un numar natural
        Se arunca exceptie daca exista studentul cu idStudent in lista
        '''
        for el in self.__repo.getListaStudenti():
            if el.getIDStudent() == idStudent:
                raise RepositoryException('    Exista deja un student cu acest id')
    def validaremodificareStudent(self,idActual,student):
        '''
        Metoda valideaza instanta student si daca acesta se poate inlocui cu studentul vaand idActual
        idActual - string ce reprezinta un numar natural
        student - instanta a clasei student
        Se arunca exceptie daca idStudent daca exista idStudentului in lista si este diferit de idActual sau daca numele este vid
        '''
        ok = True
        for el in self.__repo.getListaStudenti():
            if el.getIDStudent() == student.getIDStudent():
                ok = False
        if idActual == student.getIDStudent():
            ok = True
        if ok == False:
            raise RepositoryException('    Exista deja un student cu acest id')
class ValidatorRepStudent2():
    '''
    Tip abstract de date ce defineste un validator pentru Studenti2
    Domain: {repo este clasa in care se retin Studentii}
    '''
    def __init__(self,repo):
        '''
        Crearea unei noi instante ValidatorRepStudent
        '''
        self.__repo = repo
    def existaIdStudent(self,idStudent):
        '''
        Metoda valideaza si verifica daca exista idStudent
        idStudent - string ce reprezinta un numar natural
        Arunca exceptie daca idStudentului nu exista studentul cu idStudent in lista
        '''
        ok = False
        with open(self.__repo.getFisier()) as fh:
            for linie in fh:
                lin = linie.strip()
                if lin != '':
                    t = lin.split(',')
                    if t[0] == idStudent:
                        ok = True
        if ok == False:
            raise RepositoryException('    Nu exista studentul cu acest id')
    def nuExistaIdStudent(self,idStudent):
        '''
        Metoda verifica daca nu exista studentul cu idStudent
        idStudent -string ce reprezinta un numar natural
        Se arunca exceptie daca exista studentul cu idStudent in lista
        '''
        ok = False
        with open(self.__repo.getFisier()) as fh:
            for linie in fh:
                lin = linie.strip()
                if lin != '':
                    t = lin.split(',')
                    if t[0] == idStudent:
                        ok = True
        if ok == True:
            raise RepositoryException('    Exista deja un student cu acest id')
    def validaremodificareStudent(self,idActual,student):
        '''
        Metoda valideaza instanta student si daca acesta se poate inlocui cu studentul avand idActual
        idActual - string ce reprezinta un numar natural
        student - instanta a clasei student
        Se arunca exceptie daca idStudent daca exista idStudentului in lista si este diferit de idActual sau daca numele este vid
        '''
        ok = True
        with open(self.__repo.getFisier()) as fh:
            for linie in fh:
                lin = linie.strip()
                if lin != '':
                    t = lin.split(',')
                    if t[0] == student.getIDStudent():
                        ok = False
        if idActual == student.getIDStudent():
            ok = True
        if ok == False:
            raise RepositoryException('    Exista deja un student cu acest id')
class RepositoryDisciplina:
    '''
    Tip abstract de date pentru retinerea Disciplinelor
    '''
    def __init__(self):
        '''
        Crearea unei noi instante RepositoryDisciplina
        '''
        self.__listaDisciplina = []
    def getListaDiscipline(self):
        '''
        Metoda returneaza listaDisciplinelor
        '''
        return self.__listaDisciplina
    def adauga(self,disciplina):
        '''
        Metoda adauga in listaDisciplinelor disciplina
        disciplina - instanta a clasei disciplina
        '''
        self.getListaDiscipline().append(disciplina)
    def sterge(self,idDisciplina):
        '''
        Metoda sterge Disciplina cu idDisciplina din listaDiscipline
        idDisciplina - string ce reprezinta numar natural        
        '''
        lista = self.getListaDiscipline()
        i = 0
        while True:
            if lista[i].getIDdisciplina() == idDisciplina:
                lista.pop(i)
                break
            i += 1
    def modifica(self,idActual,disciplina):
        '''
        Metoda modifica Disciplina cu idActual cu disciplina
        disciplina - instanta a clasei Disciplina
        idActual - string ce reprezinta numar natural        
        '''
        lista = self.getListaDiscipline()
        i = 0
        while True:
            if lista[i].getIDdisciplina() == idActual:
                lista[i].setIDdisciplina(disciplina.getIDdisciplina())
                lista[i].setNume(disciplina.getNume()) 
                lista[i].setProfesor(disciplina.getProfesor())
                break
            i += 1
    def cauta(self,idDisciplina):
        '''
        Metoda cauta Disciplina cu idDisciplina si returneaza un string cu numele, profesorul si id acesteia
        idDisciplina - string ce reprezinta numar natural        
        '''
        lista = self.getListaDiscipline()
        i = 0
        while True:
            if lista[i].getIDdisciplina() == idDisciplina:
                disciplina = lista[i]
                break
            i += 1
        return disciplina
class RepositoryDisciplinaFisier(RepositoryDisciplina):
    '''
    Tip abstract de date pentru retinerea disciplinelor in fisier
    '''
    def __init__(self,numeFisier):
        '''
        Crearea unei noi instante care mosteneste instanta RepositoryDisciplina
        '''
        RepositoryDisciplina.__init__(self)
        self.__numeFisier = numeFisier
        self.citireFisier(numeFisier)
    def citireFisier(self,numeFisier):
        '''
        Introducerea datelor din fisier in program
        '''
        with open(numeFisier) as fh:
            for line in fh:
                lin = line.strip()
                if line != '':
                    t=lin.split(',')
                    RepositoryDisciplinaFisier.adauga(self,Disciplina(t[0],t[1],t[2]))
    def stocareFisier(self,numeFisier):
        '''
        Actualizareafisierului cu datele din program
        '''
        f = open(numeFisier,'w')
        lista = self.getListaDiscipline()
        lungime = len(lista)
        if lungime >= 1:
            f.write(lista[0].getIDdisciplina()+','+lista[0].getNume()+','+lista[0].getProfesor())
        for i in range(1,lungime):
            f.write('\n'+lista[i].getIDdisciplina()+','+lista[i].getNume()+','+lista[i].getProfesor())
        f.close()
    def adauga(self, disciplina):
        '''
        Aceeasi metoda adauga la finalul careia datele se actualizeaza in fisier
        '''
        RepositoryDisciplina.adauga(self, disciplina)
        self.stocareFisier(self.__numeFisier)
    def sterge(self, idDisciplina):
        '''
        Aceeasi metoda sterge la finalul careia datele se actualizeaza in fisier
        '''
        RepositoryDisciplina.sterge(self, idDisciplina)
        self.stocareFisier(self.__numeFisier)
    def modifica(self, idActual, disciplina):
        '''
        Aceeasi metoda modifica la finalul careia datele se actualizeaza in fisier
        '''
        RepositoryDisciplina.modifica(self, idActual, disciplina)
        self.stocareFisier(self.__numeFisier)
class ValidatorRepDisciplina():
    '''
    Tip abstract de date ce defineste un validator pentru Discipline
    Domain: {repo este clasa in care se retin Disciplinele}
    '''
    def __init__(self,repo):
        '''
        Crearea unei noi instante ValidatorRepDisciplina
        '''
        self.__repo = repo    
    def existaIdDisciplina(self,idDisciplina):
        '''
        Metoda valideaza si verifica daca exista idDisciplina
        idDisciplina - string ce reprezinta un numar natural
        Se arunca exceptie daca idDisciplina nu exista in lista
        '''
        ok = False
        for el in self.__repo.getListaDiscipline():
            if el.getIDdisciplina() == idDisciplina:
                ok = True
        if ok == False:
            raise RepositoryException('    Nu exista studentul cu acest id')
    def nuExistaIdDisciplina(self,idDisciplina):
        '''
        Metoda verifica daca nu exista studentul cu idDisciplina
        idDisciplina - string ce reprezinta un numar natural
        Se arunca exceptie daca idDisciplina exista in lista
        '''
        #Luam la rand disciplinele din listaDiscipline si verificam daca exista disciplina cu idDisciplina
        for el in self.__repo.getListaDiscipline():
            if el.getIDdisciplina() == idDisciplina:
                raise RepositoryException('    Exista deja o disciplina cu acest id')
    def validaremodificareDisciplina(self,idActual,disciplina):
        '''
        Metoda valideaza instanta disciplina si daca acesta se poate inlocui cu disciplina avand idActual
        idActual - string ce reprezinta un numar natural
        disciplina - instanta a clasei disciplina
        Se arunca exceptie daca mai exista disciplina cu idDisciplina diferit de idActual
        '''
        ok = True
        for el in self.__repo.getListaDiscipline():
            if el.getIDdisciplina() == disciplina.getIDdisciplina():
                ok = False
        if idActual == disciplina.getIDdisciplina():
            ok = True
        if ok == False:
            raise RepositoryException('    Exista deja o disciplina cu acest id') 
class RepositoryNote():
    '''
    Tip abstract de date pentru retinerea Notelor acordate
    '''
    def __init__(self):
        '''
        Crearea unei noi instante RepositoryNote
        '''
        self.__listNote = []
    def getListaNote(self):
        '''
        Metoda returneaza lista Notelor
        '''
        return self.__listNote
    def adaugareNota(self,notare):
        '''
        Metoda adauga in repository o instanta a obiectului notare
        notare - o instanta a obiectului Notare 
        '''
        self.getListaNote().append(notare)
    def stergereNota(self,idStudent,idDisciplina):
        '''
        Metoda sterge din repository o instanta a obiectului notare cu idStudent si idDisciplina
        idStudent,idDisciplina - string ce reprezinta numar natural   
        '''
        lista = self.getListaNote()
        el = 0
        while True:
            if lista[el].getStudent().getIDStudent() == idStudent and lista[el].getDisciplina().getIDdisciplina() == idDisciplina:
                lista.pop(el)
                break
            el += 1    
class RepositoryNoteFisier(RepositoryNote):
    '''
    Tip abstract de date pentru retinerea notelor in fisier
    '''
    def __init__(self,numeFisier,listaStudenti,listaDiscipline):
        '''
        Crearea unei noi instante care mosteneste instanta RepositoryNote
        '''
        RepositoryNote.__init__(self)
        self.__numeFisier = numeFisier
        self.__listaStudenti=listaStudenti
        self.__listaDiscipline=listaDiscipline
        self.citireFisier(numeFisier)
    def citireFisier(self,numeFisier):
        '''
        Introduce in program datele din fisier
        '''
        with open(numeFisier) as fh:
            for linie in fh:
                lin = linie.strip()
                if lin != '':
                    t=lin.split(',')
                    student = RepositoryStudentFisier.cauta(self, t[0])
                    disciplina = RepositoryDisciplinaFisier.cauta(self, t[1])
                    RepositoryNoteFisier.adaugareNota(self, Notare(student,disciplina,t[2]))
    def getListaStudenti(self):
        '''
        Returneaza lista de Studenti
        '''
        return self.__listaStudenti
    def getListaDiscipline(self):
        '''
        Returneaza lista de Discipline
        '''
        return self.__listaDiscipline
    def stocareFisier(self,numeFisier):
        '''
        Actualizarea datelor din fisier cu cele din program
        '''
        f=open(numeFisier,'w')
        lista=self.getListaNote()
        lungime=len(lista)
        if lungime>=1:
            f.write(lista[0].getStudent().getIDStudent()+','+lista[0].getDisciplina().getIDdisciplina()+','+lista[0].getNota())
        for i in range(1,lungime):
            f.write('\n'+lista[i].getStudent().getIDStudent()+','+lista[i].getDisciplina().getIDdisciplina()+','+lista[i].getNota())
    def adaugareNota(self, notare):
        '''
        Aceeasi metoda adaugaNota la finalul careia datele se actualizeaza in fisier
        '''
        RepositoryNote.adaugareNota(self, notare)
        self.stocareFisier(self.__numeFisier)
    def stergereNota(self, idStudent, idDisciplina):
        '''
        Aceeasi metoda stergeNota la finalul careia datele se actualizeaza in fisier
        '''
        RepositoryNote.stergereNota(self, idStudent, idDisciplina)
        self.stocareFisier(self.__numeFisier)
class ValidatorRepNota():
    '''
    Tip abstract de date ce defineste un validator pentru adaugarea de Nota
    Domain: {repoS, repoD este clasa in care se retin Studentii si respectiv Disciplinele}
    '''
    def __init__(self,repoS,repoD,repoN):
        '''
        Crearea unei noi instante ValidatorRepNota
        '''
        self.__repoS = repoS
        self.__repoD = repoD
        self.__repoN = repoN
    def existaStudentulSiDisciplina(self,idStudent,idDisciplina):
        '''
        Metoda verifica daca exista studentul cu idStudent in lista de studenti si disciplina cu idDisciplina in lista de discipline
        idStudent, idDiciplina - string ce reprezinta un numar natural
        Se arunca exceptie daca idStudent sau idDisciplina nu este un string ce reprezinta un numar natural
        '''
        eroare = ''
        ok = False
        for el in self.__repoS.getListaStudenti():
                if el.getIDStudent() == idStudent:
                    ok = True
        if ok == False:
            if eroare != '':
                eroare = eroare + '\n'
            eroare = eroare + '    Nu exista studentul cu acest id'
        ok = False   
        for el in self.__repoD.getListaDiscipline():
            if el.getIDdisciplina() == idDisciplina:
                ok = True
        if ok == False:
            if eroare != '':
                eroare = eroare + '\n'
            eroare = eroare + '    Nu exista disciplina cu acest id'
        if eroare != '':
            raise RepositoryException(eroare)
    def existaNota(self,idStudent,idDisciplina):
        '''
        Metoda verifica daca exista studentul idStudent care are la disciplina idDisciplina o nota
        idStudent, idDiciplina - string ce reprezinta un numar natural
        Se arunca exceptie daca idStudent nu are nota la idDisciplina
        '''
        ok = False
        for el in self.__repoN.getListaNote():
            if el.getStudent().getIDStudent() == idStudent and el.getDisciplina().getIDdisciplina() == idDisciplina:
                ok = True
        if ok == False:
            raise RepositoryException('    Acest elev nu are o nota la aceasta disciplina')
    def nuExistaNota(self,idStudent,idDisciplina):
        '''
        Metoda verifica daca nu exista studentul idStudent care are la disciplina idDisciplina o nota
        idStudent, idDiciplina - string ce reprezinta un numar natural
        Se arunca exceptie daca idStudent are nota la idDisciplina
        '''
        ok = False
        for el in self.__repoN.getListaNote():
            if el.getStudent().getIDStudent() == idStudent and el.getDisciplina().getIDdisciplina() == idDisciplina:
                ok = True
        if ok == True:
            raise RepositoryException('    Acest elev are deja o nota la aceasta disciplina')
class RepositoryNoteDisciplineDTO():
    '''
    Tip abstract de date pentru retinerea listei de studenti si ale notelor acestora la o disciplina
    '''
    def __init__(self):
        '''
        Crearea unei noi instante RepositoryNoteDisciplineDTO
        '''
        self.__listaNoteDisciplina = []
    def getListaNoteDisciplina(self):
        '''
        Metoda returneaza lista obiectelor NoteDisciplineDTO
        '''
        return self.__listaNoteDisciplina
    def adaugaStudent(self,studentDTO):
        '''
        Metoda adauga in lista un nou obiect NoteDisciplineDTO
        '''
        self.getListaNoteDisciplina().append(studentDTO)
class RepositoryMedieDisciplineDTO():
    '''
    Tip abstract de date pentru retinerea listei de studenti si ale mediei tuturor notelor acestora
    '''
    def __init__(self):
        '''
        Crearea unei noi instante RepositoryMedieDisciplineDTO
        '''
        self.__listaMedieDisciplineDTO = []
    def getListaMedieDisciplineDTO(self):
        '''
        Metoda returneaza lista obiectelor RepositoryMedieDisciplineDTO
        '''
        return self.__listaMedieDisciplineDTO
    def adaugaMedie(self,medieDisciplineDTO):
        '''
        Metoda adauga in lista un nou obiect MedieDisciplineDTO
        '''
        self.getListaMedieDisciplineDTO().append(medieDisciplineDTO)
class RepositoryMedieNoteAcordateDTO():
    '''
    Tip abstract de date pentru retinerea listei de profesori si ale mediei tuturor notelor acordate de acestia
    '''
    def __init__(self):
        '''
        Crearea unei noi instante RepositoryMedieNoteAcordateDTO
        '''
        self.__listaMedieNoteAcordateDTO = []
    def getListaMedieNoteAcordateDTO(self):
        '''
        Metoda returneaza lista obiectelor RepositoryMedieNoteAcordateDTO
        '''
        return self.__listaMedieNoteAcordateDTO
    def adaugaMedie(self,medieNoteAcordateDTO):
        '''
        Metoda adauga in lista un nou obiect MedieNoteAcordateDTO
        '''
        self.getListaMedieNoteAcordateDTO().append(medieNoteAcordateDTO)
class TestRepository(unittest.TestCase):
    def test_RepositoryStudent(self):
        repoS = RepositoryStudent()
        self.assertTrue( repoS.getListaStudenti() == [])
        repoS.adauga(Student(1,'Adi'))
        repoS.adauga(Student(2,'Mihai'))
        self.assertTrue( len(repoS.getListaStudenti()) == 2)
        repoS.sterge(1)
        self.assertTrue( len(repoS.getListaStudenti()) == 1)
        repoS.modifica(2, Student(2,'Andrei'))
        lista = repoS.getListaStudenti()
        self.assertTrue( lista[0].getIDStudent() == 2)
        self.assertTrue( lista[0].getNume() == 'Andrei')
        repoS.adauga2(Student(1,'Andrei'))
        lista = repoS.getListaStudenti()
        self.assertTrue( lista[1].getIDStudent() == 1)
        self.assertTrue( lista[1].getNume() == 'Andrei(2)')
        student = repoS.cauta(2)
        self.assertTrue( student.getNume() == 'Andrei')
    def test_ValidatorRepStudent(self):
        repoS = RepositoryStudent()
        valS = ValidatorRepStudent(repoS)
        repoS.adauga(Student(1,'Adi'))
        repoS.adauga(Student(2,'Mihai'))
        with self.assertRaises(RepositoryException):
            valS.nuExistaIdStudent(2)
        with self.assertRaises(RepositoryException):
            valS.existaIdStudent(3)
        with self.assertRaises(RepositoryException):
            valS.validaremodificareStudent(2, Student(1,''))
        with self.assertRaises(RepositoryException):
            valS.validaremodificareStudent(1,Student(2,'Ana'))
        with self.assertRaises(RepositoryException):
            valS.validaremodificareStudent(2,Student(1,'Petru'))
    def test_RepositoryDisciplina(self):
        repoD = RepositoryDisciplina()
        self.assertTrue( repoD.getListaDiscipline() == [])
        repoD.adauga(Disciplina(1,'mate','Andone'))
        repoD.adauga(Disciplina(2,'fizica','Bonta'))
        self.assertTrue( len(repoD.getListaDiscipline()) == 2)
        repoD.sterge(1)
        self.assertTrue( len(repoD.getListaDiscipline()) == 1)
        repoD.modifica(2, Disciplina(2,'chimie','Laslau'))
        lista = repoD.getListaDiscipline()
        self.assertTrue( lista[0].getIDdisciplina() == 2)
        self.assertTrue( lista[0].getNume() == 'chimie')
        self.assertTrue( lista[0].getProfesor() == 'Laslau')
        disciplina = repoD.cauta(2)
        self.assertTrue( disciplina.getProfesor() == 'Laslau')
    def test_ValidatorRepDisciplina(self):
        repoD = RepositoryDisciplina()
        valD = ValidatorRepDisciplina(repoD)
        repoD.adauga(Disciplina(1,'mate','Andone'))
        repoD.adauga(Disciplina(2,'chimie','Laslau'))
        with self.assertRaises(RepositoryException):
            valD.nuExistaIdDisciplina(2)
        with self.assertRaises(RepositoryException):
            valD.existaIdDisciplina(3)
        with self.assertRaises(RepositoryException):
            valD.validaremodificareDisciplina(2, Disciplina(1,'',''))
        with self.assertRaises(RepositoryException):
            valD.validaremodificareDisciplina(1, Disciplina(2,'BIO','negrea'))
    def test_RepositoryNote(self):
        repoN = RepositoryNote()
        repoS = RepositoryStudent()
        repoD = RepositoryDisciplina()
        repoD.adauga(Disciplina(1,'mate','Andone'))
        repoD.adauga(Disciplina(2,'fizica','Bonta'))
        repoS.adauga(Student(1,'Adi'))
        repoS.adauga(Student(2,'Mihai'))
        self.assertTrue( len(repoN.getListaNote()) == 0)
        repoN.adaugareNota(Notare(Student(1,'Adi'), Disciplina(1,'mate','Andone'), '7.05'))
        repoN.adaugareNota(Notare(Student(2,'Mihai'), Disciplina(2,'fizica','Bonta'), '7.00'))
        lista = repoN.getListaNote()
        self.assertTrue( lista[0].getStudent().getIDStudent() == 1)
        self.assertTrue( lista[0].getDisciplina().getIDdisciplina() == 1)
        self.assertTrue( lista[0].getNota() == '7.05')
        repoN.stergereNota(1, 1)
        self.assertTrue( lista[0].getStudent().getIDStudent() == 2)
        self.assertTrue( lista[0].getDisciplina().getIDdisciplina() == 2)
        self.assertTrue( lista[0].getNota() == '7.00')
    def test_ValidatorRepNota(self):
        repoD = RepositoryDisciplina()
        repoD.adauga(Disciplina(1,'mate','Andone'))
        repoD.adauga(Disciplina(2,'fizica','Bonta'))
        repoS = RepositoryStudent()
        repoS.adauga(Student(1,'Adi'))
        repoS.adauga(Student(2,'Teo'))
        repoN = RepositoryNote()
        repoN.adaugareNota(Notare(Student(1,'Adi'),Disciplina(1,'mate','Andone'),9))
        valN = ValidatorRepNota(repoS,repoD,repoN)
        with self.assertRaises(RepositoryException):
            valN.existaStudentulSiDisciplina(1,3)
        with self.assertRaises(RepositoryException):
            valN.existaStudentulSiDisciplina(3,2)
        with self.assertRaises(RepositoryException):
            valN.existaNota(1, 2)
        with self.assertRaises(RepositoryException):
            valN.existaNota(2, 1)
        with self.assertRaises(RepositoryException):
            valN.nuExistaNota(1, 1)
    def test_RepositoryNoteDisciplineDTO(self):
        repoDTO = RepositoryNoteDisciplineDTO()
        self.assertTrue( len(repoDTO.getListaNoteDisciplina()) == 0)
        repoDTO.adaugaStudent(NoteDisciplinaDTO('Ana',10))
        lista = repoDTO.getListaNoteDisciplina()
        self.assertTrue( lista[0].getNume() == 'Ana')
        self.assertTrue( lista[0].getNota() == 10)
    def test_RepositoryMedieDisciplineDTO(self):
        repoDTO = RepositoryMedieDisciplineDTO()
        self.assertTrue( len(repoDTO.getListaMedieDisciplineDTO()) == 0)
        repoDTO.adaugaMedie(MedieDisciplineDTO('Ana',10))
        lista = repoDTO.getListaMedieDisciplineDTO()
        self.assertTrue( lista[0].getNume() == 'Ana')
        self.assertTrue( lista[0].getMedia() == 10)
    def test_RepositoryMedieNoteAcordateDTO(self):
        repoDTO = RepositoryMedieNoteAcordateDTO()
        self.assertTrue( len(repoDTO.getListaMedieNoteAcordateDTO()) == 0)
        repoDTO.adaugaMedie(MedieNoteAcordateDTO('Ana',10))
        lista = repoDTO.getListaMedieNoteAcordateDTO()
        self.assertTrue( lista[0].getNume() == 'Ana')
        self.assertTrue( lista[0].getMedia() == 10)
    def test_RepositoryStudentFisier(self):
        repoS = RepositoryStudentFisier('testDateStudenti.txt')
        lista = repoS.getListaStudenti()
        self.assertTrue(len(lista)==4)
        self.assertTrue(lista[3].getNume() == 'Cristina')
        self.assertTrue(lista[2].getIDStudent() == '3')
    def test_RepositoryDisciplinaFisier(self):
        repoD = RepositoryDisciplinaFisier('testDateDiscipline.txt')
        lista = repoD.getListaDiscipline()
        self.assertTrue(len(lista)==3)
        self.assertTrue(lista[0].getNume() == 'mate')
        self.assertTrue(lista[2].getIDdisciplina() == '3')
        self.assertTrue(lista[1].getProfesor() == 'Bonta')
    def test_RepositoryNoteFisier(self):
        repoS = RepositoryStudentFisier('testDateStudenti.txt')
        repoD = RepositoryDisciplinaFisier('testDateDiscipline.txt')
        repoN = RepositoryNoteFisier('testDateNote.txt',repoS.getListaStudenti(),repoD.getListaDiscipline())
        lista = repoN.getListaNote()
        self.assertTrue(len(lista)==3)
        self.assertTrue(lista[0].getNota()=='10')
        self.assertTrue(lista[1].getStudent().getNume()=='Petru')
        self.assertTrue(lista[2].getDisciplina().getProfesor() == 'Laslau')
if __name__ == '__main__':
    unittest.main()