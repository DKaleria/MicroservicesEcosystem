package by.javaguru.identityservice.usecaseses.mapper;

import by.javaguru.identityservice.database.entity.User;
import by.javaguru.identityservice.usecaseses.events.AuthUserGotEvent;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",
        builder = @Builder(disableBuilder = true))
public interface AuthUserMapper {
    AuthUserGotEvent userToAuthUserGotEvent(User user);

    User authUserGotEventToUser(AuthUserGotEvent authUserGotEvent);
}
