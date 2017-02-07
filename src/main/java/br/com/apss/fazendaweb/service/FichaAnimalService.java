package br.com.apss.fazendaweb.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.apss.fazendaweb.model.Animal;
import br.com.apss.fazendaweb.model.FichaAnimal;
import br.com.apss.fazendaweb.repository.FichaAnimalRepository;
import br.com.apss.fazendaweb.util.NegocioException;
import br.com.apss.fazendaweb.util.Transactional;

public class FichaAnimalService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FichaAnimalRepository fichaAnimalRepository;

	@Transactional
	public FichaAnimal salvar(FichaAnimal fichaAnimal) {
		return fichaAnimalRepository.save(fichaAnimal);
	}

	@Transactional
	public void remover(FichaAnimal fichaAnimal) {
		fichaAnimalRepository.remove(fichaAnimal);
	}

	public FichaAnimal buscarPorId(Long id) {
		return fichaAnimalRepository.porId(id);
	}

	public List<FichaAnimal> listarTodos() {
		return fichaAnimalRepository.listarTodos();
	}

	public List<FichaAnimal> grupoCondicao(FichaAnimal op) {
		return fichaAnimalRepository.grupoCondicao(op);
	}

	public FichaAnimal porId(Long id) {
		return fichaAnimalRepository.porId(id);
	}

	public List<FichaAnimal> porTipoLanc(String tipoLanc) {
		return fichaAnimalRepository.porTipoLanc(tipoLanc);
	}
	
	public List<FichaAnimal> porTipoLancParto() {
		return fichaAnimalRepository.porTipoLancParto();
	}
	
	public Boolean verificaCobertura(Animal animal, Boolean edicao, FichaAnimal cobertura) {
		List<FichaAnimal> animais = fichaAnimalRepository.porAnimal(animal);
		if (animais != null) {
			for (FichaAnimal fichaAnimal : animais) {
				if (fichaAnimal.getResultado() == null && edicao == false) {
					throw new NegocioException(
							"J� existe uma cobertura lan�anda para esse animal sem a defini��o do resultando.");
				} else if (fichaAnimal.getResultado() == null && edicao == true) {
					if (cobertura.getDtDiagnostico() == null || cobertura.getResultado() == null) {
						throw new NegocioException("O campo data diagn�stico e resultado s�o obrigat�rio.");
					}
				} else if (fichaAnimal.getResultado() != null && edicao == true) {
					if (cobertura.getDtDiagnostico() == null || cobertura.getResultado() == null) {
						throw new NegocioException("O campo data diagn�stico e resultado s�o obrigat�rio.");
					} 
				} else if (fichaAnimal.getResultado().contains("POSITIVO") && fichaAnimal.getDtParto() == null) {
					throw new NegocioException(
							"Existe uma cobertura para esse animal com resultado do diagn�stico positivo, "
									+ "portanto ser� necessario esperar o parto do mesmo.");
				}
			}
		}
		return false;
	}
	
	public List<FichaAnimal> buscarPraParto() {
		return fichaAnimalRepository.buscarPraParto();
	}

}
