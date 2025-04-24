package edu.miu.cs489.adswebapp.service.impl;

import edu.miu.cs489.adswebapp.dto.request.DentistRequestDTO;
import edu.miu.cs489.adswebapp.dto.response.DentistResponseDTO;
import edu.miu.cs489.adswebapp.exception.dentist.DentistNotFoundException;
import edu.miu.cs489.adswebapp.mapper.DentistMapper;
import edu.miu.cs489.adswebapp.model.Dentist;
import edu.miu.cs489.adswebapp.respository.DentistRepository;
import edu.miu.cs489.adswebapp.respository.UserRepository;
import edu.miu.cs489.adswebapp.security.exception.user.DuplicateUserFieldException;
import edu.miu.cs489.adswebapp.security.model.Role;
import edu.miu.cs489.adswebapp.service.DentistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DentistServiceImpl implements DentistService {

    private final DentistRepository dentistRepository;
    private final DentistMapper     dentistMapper;
    private final UserRepository    userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<DentistResponseDTO> getAllDentists(int page, int size, String sortBy, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));

        return dentistRepository.findAll(pageRequest).map(dentistMapper::dentistToDentistResponseDTO);
    }

    @Override
    public DentistResponseDTO getDentistByUsername(String username) {
        Dentist dentist = dentistRepository.findByUsername(username)
                                           .orElseThrow(() -> new DentistNotFoundException(
                                                   String.format("No dentist found with username: %s", username)));

        return dentistMapper.dentistToDentistResponseDTO(dentist);
    }

    @Override
    public DentistResponseDTO getDentistByDentistId(String dentistId) {
        Dentist dentist = dentistRepository.findByDentistId(dentistId)
                                           .orElseThrow(() -> new DentistNotFoundException(
                                                   String.format("No dentist found with dentist id: %s", dentistId)));

        return dentistMapper.dentistToDentistResponseDTO(dentist);
    }

    @Override
    public DentistResponseDTO addADentist(DentistRequestDTO dentistRequestDTO) {
        Dentist dentist = dentistMapper.dentistRequestDTOToDentist(dentistRequestDTO);
        userRepository.findByEmail(dentist.getEmail()).ifPresent((a) -> {
            throw new DuplicateUserFieldException(String.format("Email %s already exists", dentist.getEmail()));
        });

        userRepository.findByUsername(dentist.getUsername()).ifPresent((a) -> {
            throw new DuplicateUserFieldException(String.format("Username %s already exists", dentist.getUsername()));
        });

        String nextDentistId = null;

        Optional<Dentist> optionalDentist = dentistRepository.findTopByOrderByIdDesc();

        if(optionalDentist.isPresent()){
            Dentist topDentist = optionalDentist.get();

            String currentDentistId =
                    topDentist.getDentistId().split(Dentist.DENTIST_ID_PREFIX)[1];

            nextDentistId =
                    Dentist.DENTIST_ID_PREFIX + (Integer.parseInt(currentDentistId) + 1);
        } else {
            nextDentistId =
                    Dentist.DENTIST_ID_PREFIX + 1;
        }


        dentist.setPassword(passwordEncoder.encode(dentist.getPassword()));
        dentist.setDentistId(nextDentistId);
        dentist.setRole(Role.DENTIST);

        Dentist savedDentist = dentistRepository.save(dentist);

        return dentistMapper.dentistToDentistResponseDTO(savedDentist);
    }
}
