package org.launchcode.codingevents.data;

import org.launchcode.codingevents.models.EventDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDetailsRepository extends CrudRepository <EventDetails, Integer> {
}
