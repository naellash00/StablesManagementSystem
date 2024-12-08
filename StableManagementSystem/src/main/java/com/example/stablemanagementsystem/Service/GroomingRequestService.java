package com.example.stablemanagementsystem.Service;

import com.example.stablemanagementsystem.Model.GroomingRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroomingRequestService {
    private List<GroomingRequest> groomingRequests = new ArrayList<>();

    public List<GroomingRequest> getGroomingRequests() {
        return groomingRequests;
    }

    public void addGroomingRequest(GroomingRequest request) {
        groomingRequests.add(request);
    }
}
