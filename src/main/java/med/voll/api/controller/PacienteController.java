package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosPaciente dados) {
        var paciente = new Paciente(dados);
        this.pacienteRepository.save(paciente);
    }

    @GetMapping
    public Page<DadosListagemPaciente> listar(@PageableDefault(sort = {"nome"}, size = 10) Pageable paginacao) {
        return this.pacienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        var paciente = this.pacienteRepository.getReferenceById(dados.id());
        paciente.atualizarDados(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        var paciente = this.pacienteRepository.getReferenceById(id);
        paciente.excluir();
    }
}
