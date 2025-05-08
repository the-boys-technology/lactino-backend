package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.request.LaticinioDTO;

import java.util.UUID;

public interface LaticinioService {

    UUID salvarLaticinio(LaticinioDTO laticinioDTO);
}
