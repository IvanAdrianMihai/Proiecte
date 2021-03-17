
'''
Created on Nov 11, 2019

@author: Ivan Adrian
'''

from UI import Ui
from Repository import RepositoryDisciplina,RepositoryStudent,RepositoryNote,\
    RepositoryNoteDisciplineDTO,RepositoryStudentFisier,\
    RepositoryDisciplinaFisier, RepositoryNoteFisier,RepositoryMedieDisciplineDTO,RepositoryMedieNoteAcordateDTO
#repoS = RepositoryStudent2('dateStudenti.txt')
repoS = RepositoryStudentFisier('dateStudenti.txt')
repoD = RepositoryDisciplinaFisier('dateDiscipline.txt') 
repoN = RepositoryNoteFisier('dateNote.txt',repoS.getListaStudenti(),repoD.getListaDiscipline())
repoNoteDTO =RepositoryNoteDisciplineDTO()
repoMedieDTO=RepositoryMedieDisciplineDTO()
repoAcordateDTO=RepositoryMedieNoteAcordateDTO()
ui = Ui(repoS,repoD,repoN,repoNoteDTO,repoMedieDTO,repoAcordateDTO)
ui.meniu()