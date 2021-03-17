'''
Created on Nov 9, 2019

@author: Ivan Adrian
'''
import unittest
class Student:
    '''
    Tip abstract de date pentru student
    Domain: {IDStudent, nume unde IDStudent string al unui numar natural si nume string != ''}
    '''
    def __init__(self,IDStudent,nume):
        '''
        Crearea unei noi initiante student
        '''
        self.__IDStudent = IDStudent
        self.__nume = nume
    def getIDStudent(self):
        '''
        Returneaza IDStudent 
        '''
        return self.__IDStudent
    def getNume(self):
        '''
        Returneaza numele studentului
        '''
        return self.__nume
    def setIDStudent(self,idNou):
        '''
        Schimba IDStudent cu idNou
        '''
        self.__IDStudent = idNou
    def setNume(self,numeNou):
        '''
        Schimba nume cu numeNou
        '''
        self.__nume = numeNou
class ValidatorStudent():
    '''
    Tip abstract de date ce defineste un validator pentru Studenti\
    '''
    def validareIdStudent(self,idStudent):
        '''
        Metoda valideaza idStudent
        idStudent - string ce reprezinta un numar natural
        Se arunca exceptie daca id-ul nu este un numar natural
        '''
        try:
            p = int(idStudent)
            if p < 0:
                raise ValueError
        except ValueError:
            raise ValidatorException('    Id-ul trebuie sa fie un numar natural')
    def validareStudent(self,student):
        '''
        Metoda valideaza datele instantei student
        student - instanta a clasei student
        Se arunca exceptie daca id-ul nu este un numar natural sau daca numele studentului este vid
        '''
        eroare = ''
        try:
            p = int (student.getIDStudent())
            if p < 0:
                raise ValueError
        except ValueError:
            eroare = eroare + '    Id-ul trebuie sa fie un numar natural'
        if student.getNume() == '':
            if eroare != '':
                eroare = eroare +'\n'
            eroare = eroare + '    Numele studentului nu trebuie sa fie vid'
        if eroare != '':
            raise ValidatorException(eroare)
class Disciplina:
    '''
    Tip abstract de date pentru disciplina
    Domain: {IDdisciplina, nume, profesor, unde IDdisciplina string nr. natural, nume si profesor string != ''  }
    '''
    def __init__(self,IDdisciplina,nume,profesor):
        '''
        Crearea unei noi instante disciplina
        '''
        self.__IDdisciplina = IDdisciplina
        self.__nume = nume
        self.__profesor = profesor
    def getIDdisciplina(self):
        '''
        Returneaza IDdisciplina 
        '''
        return self.__IDdisciplina
    def getNume(self):
        '''
        Returneaza numele disciplinei
        '''
        return self.__nume
    def getProfesor(self):
        '''
        Returneaza numele profesorului
        '''
        return self.__profesor
    def setIDdisciplina(self,idNou):
        '''
        Schimba IDdisciplinacu idNou
        '''
        self.__IDdisciplina = idNou
    def setNume(self,numeNou):
        '''
        Schimba numele disciplinei nume cu numeNou
        '''
        self.__nume = numeNou
    def setProfesor(self,profesorNou):
        '''
        Schimba numele profesorului profesor cu profesorNou
        '''
        self.__profesor = profesorNou
class ValidatorDisciplina():
    '''
    Tip abstract de date ce defineste un validator pentru Discipline
    '''
    def validareIdDisciplina(self,idDisciplina):
        '''
        Metoda valideaza idDisciplina
        idDisciplina - string ce reprezinta un numar natural
        Se arunca exceptie daca idDisciplina nu este numar natural
        '''
        try:
            p = int(idDisciplina)
            if p < 0:
                raise ValueError
        except ValueError:
            raise ValidatorException('    Id-ul trebuie sa fie un numar natural')
    def validareDisciplina(self,disciplina):
        '''
        Metoda valideaza datele instantei disciplina
        disciplina - instanta a clasei disciplina
        Se arunca exceptie daca idDisciplina nu este numar natural, daca umele disciplinei este vid sau daca numele profesorului este vid
        '''
        eroare = ''
        try:
            p = int(disciplina.getIDdisciplina())
            if p < 0:
                raise ValueError
        except ValueError:
            eroare = eroare + '    Id-ul trebuie sa fie un numar natural'
        if disciplina.getNume() == '':
            if eroare != '':
                eroare = eroare +'\n'
            eroare = eroare + '    Numele disciplinei nu trebuie sa fie vid'
        if disciplina.getProfesor() == '':
            if eroare != '':
                eroare = eroare +'\n'
            eroare = eroare + '    Numele profesorului nu trebuie sa fie vid'
        if eroare != '':
            raise ValidatorException(eroare)
class Notare():
    '''
    Tip abstract de date pentru notare
    Domain: {idStudent,idDisciplina,nota, unde idStudent si idDisciplina string nr. natural, nota string nr. real din [1,10]}
    '''
    def __init__(self,student,disciplina,nota):
        '''
        Crearea unei noi instante notare
        '''
        self.__student = student
        self.__disciplina = disciplina
        self.__nota = nota
    def getStudent(self):
        '''
        Returneaza student 
        '''
        return self.__student
    def getDisciplina(self):
        '''
        Returneaza disciplina
        '''
        return self.__disciplina
    def getNota(self):
        '''
        Returneaza nota
        '''
        return self.__nota
    def setStudent(self,studentNou):
        '''
        Schimba Student cu studentNou
        '''
        self.__student = studentNou
    def setDisciplina(self,disciplinaNou):
        '''
        Schimba Disicplina cu disciplinaNou
        '''
        self.__disciplina = disciplinaNou
    def setNota(self,notaNoua):
        '''
        Schimba nota cu notaNoua
        '''
        self.__nota =notaNoua
class NoteDisciplinaDTO():
    '''
    Tip abstract de date pentru nota la o disciplina
    Domain: {nume, nota  unde nume este string, iar nota string nr. real din [1,10]}
    '''
    def __init__(self,nume,nota):
        '''
        Crearea unei noi instante noteDisciplinaDTO
        '''
        self.__nume = nume
        self.__nota = nota
    def getNume(self):
        '''
        Metoda returneaza numele studentului 
        '''
        return self.__nume
    def getNota(self):
        '''
        Metoda returneaza nota studentului
        '''
        return self.__nota
    def setNume(self,numeNou):
        '''
        Metoda schimba numele cu numeNou
        '''
        self.__nume=numeNou
    def setNota(self,notaNoua):
        '''
        Metoda schimba nota cu notaNoua
        '''
        self.__nota = notaNoua
class MedieDisciplineDTO():
    '''
    Tip abstract de date pentru media unui student
    Domain: {nume, media  unde nume este string, iar nota string nr. real din [1,10]}
    '''
    def __init__(self,nume,media):
        '''
        Crearea unei noi instante MedieDisciplineDTO
        '''
        self.__nume = nume
        self.__media = media
    def getNume(self):
        '''
        Metoda returneaza numele studentului 
        '''
        return self.__nume
    def getMedia(self):
        '''
        Metoda returneaza media studentului
        '''
        return self.__media
    def setNume(self,numeNou):
        '''
        Metoda schimba numele cu numeNou
        '''
        self.__nume=numeNou
    def setMedia(self,mediaNoua):
        '''
        Metoda schimba media cu mediaNoua
        '''
        self.__media = mediaNoua  
class MedieNoteAcordateDTO():
    '''
    Tip abstract de date pentru media notelor acordate de catre un profesor
    Domain: {nume, media  unde nume este string, iar nota string nr. real din [1,10]}
    '''
    def __init__(self,nume,media):
        '''
        Crearea unei noi instante MedieNoteAcordateDTO
        '''
        self.__nume = nume
        self.__media = media
    def getNume(self):
        '''
        Metoda returneaza numele profesorului 
        '''
        return self.__nume
    def getMedia(self):
        '''
        Metoda returneaza media notelor acordate
        '''
        return self.__media
    def setNume(self,numeNou):
        '''
        Metoda schimba numele cu numeNou
        '''
        self.__nume=numeNou
    def setMedia(self,mediaNoua):
        '''
        Metoda schimba media cu mediaNoua
        '''
        self.__media = mediaNoua 
class ValidatorNota():
    '''
    Tip abstract de date ce defineste un validator pentru adaugarea de Nota
    '''
    def notareCorecta(self,idStudent,idDisciplina,nota):
        '''
        Metoda verifica daca idStudent, idDisiplina si nota sunt valide ca tip de date 
        idStudent, idDiciplina - string ce reprezinta un numar natural
        nota - string ce reprezinta un numar real din [1,10]
        Se arunca exceptie daca idStudent nu este numar natural, daca idDisciplina nu este numar natural sau daca nota nu este un numar real din intervalul [1,10]
        '''
        eroare = ''
        try:
            p = int(idStudent)
            if p < 0:
                raise ValueError
        except ValueError:
            eroare = '    Id-ul studentului trebuie sa fie un numar natural'
        try:
            p = int(idDisciplina)
            if p < 0:
                raise ValueError 
        except ValueError:
            if eroare != '':
                eroare = eroare + '\n'
            eroare = eroare + '    Id-ul disciplinei trebuie sa fie un numar natural'
        try:
            if float(nota) < 1 or float(nota) > 10:
                raise ValueError
        except ValueError:
            if eroare != '':
                eroare = eroare + '\n'
            eroare = eroare + '    Nota este un numar real ce apartine intervalului [1,10]'
        if eroare != '':
            raise ValidatorException(eroare)
class ValidatorException(Exception):
    '''
    Tip abstract de date ce defineste o clasa de exceptie
    '''
    def __init__(self,mesaje):
        '''
        Crearea unei noi instante ValidatorException
        '''
        self.__mesaje = mesaje
    def getMesaje(self):
        '''
        Metoda returneaza mesajul
        '''
        return self.__mesaje
class RepositoryException(Exception):
    '''
    Tip abstract de date ce defineste o clasa de exceptie
    '''
    def __init__(self,mesaje):
        '''
        Crearea unei noi instante RepositoryException
        '''
        self.__mesaje = mesaje
    def getMesaje(self):
        '''
        Metoda returneaza mesajul
        '''
        return self.__mesaje
class TestDomain(unittest.TestCase):
    def test_Student(self):
        st1 = Student(12,'Enescu Andrei')
        self.assertTrue( st1.getIDStudent() == 12)
        self.assertTrue( st1.getNume() == 'Enescu Andrei')
        st2 = Student(0,'Petcu Rares')
        self.assertTrue( st2.getIDStudent() == 0)
        self.assertTrue( st2.getNume() == 'Petcu Rares')
        st1.setIDStudent(5)
        self.assertTrue( st1.getIDStudent() == 5)
        st2.setNume('Andreea Ile')
        self.assertTrue( st2.getNume() == 'Andreea Ile')
    def test_ValidatorStudent(self):
        valS = ValidatorStudent()
        with self.assertRaises(ValidatorException):
            valS.validareIdStudent('abc')
        with self.assertRaises(ValidatorException):
            valS.validareStudent(Student('a','Ana'))
        with self.assertRaises(ValidatorException):
            valS.validareStudent(Student(3,''))
        with self.assertRaises(ValidatorException):
            valS.validareStudent(Student('','Petru'))
    def test_Disciplina(self):
        d1 = Disciplina(3,'matematica','Andone Cristina')
        self.assertTrue( d1.getIDdisciplina() == 3)
        self.assertTrue( d1.getNume() == 'matematica')
        self.assertTrue( d1.getProfesor() == 'Andone Cristina')
        d2 = Disciplina(6,'fizica','Bonta Marius')
        self.assertTrue( d2.getIDdisciplina() == 6)
        self.assertTrue( d2.getNume() == 'fizica')
        self.assertTrue( d2.getProfesor() == 'Bonta Marius')
        d1.setIDdisciplina(9)
        d1.setNume('chimie')
        d2.setProfesor('Laslau Mihaela')
        self.assertTrue( d1.getIDdisciplina() == 9)
        self.assertTrue( d1.getNume() == 'chimie')
        self.assertTrue( d2.getProfesor() == 'Laslau Mihaela')
    def test_ValidatorDisciplina(self):
        valD = ValidatorDisciplina()
        with self.assertRaises(ValidatorException):
            valD.validareIdDisciplina('abc')
        with self.assertRaises(ValidatorException):
            valD.validareDisciplina(Disciplina('a','FIZICA','bonta'))
        with self.assertRaises(ValidatorException):
            valD.validareDisciplina(Disciplina(3,'','bonta'))
        with self.assertRaises(ValidatorException):
            valD.validareDisciplina(Disciplina(1,'engleza',''))
    def test_Notare(self):
        no = Notare(Student(1,'Adi'),Disciplina(2,'mate','Andone'),10)
        self.assertTrue( no.getDisciplina().getIDdisciplina() == 2)
        self.assertTrue( no.getStudent().getIDStudent() == 1)
        self.assertTrue( no.getNota() == 10)
        no1 = Notare(Student(4,'Teo'),Disciplina(1,'analiza','Berinde'),7.23)
        self.assertTrue( no1.getDisciplina().getIDdisciplina() == 1)
        self.assertTrue( no1.getStudent().getIDStudent() == 4)
        self.assertTrue( no1.getNota() == 7.23)
        no1.setDisciplina(Disciplina(0,'romana','Copaceanu'))
        no1.setStudent(Student(3,'Mircea'))
        no1.setNota(6.01)
        self.assertTrue( no1.getDisciplina().getIDdisciplina() == 0)
        self.assertTrue( no1.getStudent().getIDStudent() == 3)
        self.assertTrue( no1.getNota() == 6.01)
    def test_NoteDisciplineDTO(self):
        nd = NoteDisciplinaDTO('Maria',7)
        self.assertTrue( nd.getNota() == 7)
        self.assertTrue( nd.getNume() == 'Maria')
        nd1 = NoteDisciplinaDTO('Cristina',10)
        self.assertTrue( nd1.getNota() == 10)
        self.assertTrue( nd1.getNume() == 'Cristina')
        nd.setNume("Matei")
        nd.setNota(10)
        self.assertTrue( nd.getNota() == 10)
        self.assertTrue( nd.getNume() == 'Matei')
    def test_MedieDisciplineDTO(self):
        nd = MedieDisciplineDTO('Maria',7)
        self.assertTrue( nd.getMedia() == 7)
        self.assertTrue( nd.getNume() == 'Maria')
        nd1 = MedieDisciplineDTO('Cristina',10)
        self.assertTrue( nd1.getMedia() == 10)
        self.assertTrue( nd1.getNume() == 'Cristina')
        nd.setNume("Matei")
        nd.setMedia(10)
        self.assertTrue( nd.getMedia() == 10)
        self.assertTrue( nd.getNume() == 'Matei')
    def test_MedieNoteAcordateDTO(self):
        nd = MedieNoteAcordateDTO('Maria',7)
        self.assertTrue( nd.getMedia() == 7)
        self.assertTrue( nd.getNume() == 'Maria')
        nd1 = MedieNoteAcordateDTO('Cristina',10)
        self.assertTrue( nd1.getMedia() == 10)
        self.assertTrue( nd1.getNume() == 'Cristina')
        nd.setNume("Matei")
        nd.setMedia(10)
        self.assertTrue( nd.getMedia() == 10)
        self.assertTrue( nd.getNume() == 'Matei')
    def test_ValidatorNota(self):
        valN = ValidatorNota()
        with self.assertRaises(ValidatorException):
            valN.notareCorecta('a', 1, '10')
        with self.assertRaises(ValidatorException):
            valN.notareCorecta(3, 1, '')
        with self.assertRaises(ValidatorException):
            valN.notareCorecta(1, '', '3.2')
        with self.assertRaises(ValidatorException):
            valN.notareCorecta('', 3, '8')
        with self.assertRaises(ValidatorException):
            valN.notareCorecta(1, 1, '11')
        with self.assertRaises(ValidatorException):
            valN.notareCorecta(3, 1, '0.1')
        with self.assertRaises(ValidatorException):
            valN.notareCorecta('1.2', 1, '1')
    def test_ValidatorExcetion(self):
        with self.assertRaises(ValidatorException):
            raise ValidatorException('Eroare')
    def test_RepositoryException(self):
        with self.assertRaises(RepositoryException):
            raise RepositoryException('Eroare')
if __name__ == '__main__':
    unittest.main()