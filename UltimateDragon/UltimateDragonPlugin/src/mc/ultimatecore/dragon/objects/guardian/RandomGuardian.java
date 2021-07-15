package mc.ultimatecore.dragon.objects.guardian;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import mc.ultimatecore.dragon.objects.implementations.IGuardian;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
public class RandomGuardian {
    private final Map<IGuardian, Integer> chances;

    public IGuardian getRandom(){
        int r = new Random().nextInt(100);
        List<PercentageGuardian> guardians = new ArrayList<>();
        chances.forEach((iGuardian, integer) -> guardians.add(new PercentageGuardian(iGuardian, integer)));
        guardians.sort((o1, o2) -> o2.percentage - o1.percentage);
        for(PercentageGuardian guardian : guardians)
            if(r < guardian.percentage)
                return guardian.iGuardian;
        return guardians.isEmpty() ? null : guardians.get(0).iGuardian;
    }

    @AllArgsConstructor
    private static class PercentageGuardian{
        private final IGuardian iGuardian;
        private final int percentage;
    }
}
